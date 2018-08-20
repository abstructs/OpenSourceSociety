package reductions

import java.lang.AssertionError

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    var counter = 0
    var i = 0
    while(i < chars.length) {
      if(chars(i) == '(') counter += 1
      else if (chars(i) == ')') counter -= 1

      if(counter < 0) return false
      else i += 1
    }

    counter == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) = {
      if(idx >= until) return (arg1, arg2)
      traverse(idx + 1, until,
        if(chars(idx) == '(') arg1 + 1 else arg1,
        if(chars(idx) == ')') arg2 - 1 else arg2)
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if((until - from) / 2 < threshold) return if(balance(chars.slice(from, until))) (0, 0) else throw new AssertionError
      if(until - from < 2) return traverse(from, until, 0, 0)

      val mid = (from + until) / 2

      val ((x1, x2), (y1, y2)) = parallel(reduce(from, mid), reduce(mid, until))

      if((x1 + x2) < (y1 + y2)) throw new AssertionError

      (x1 + x2, y1 + y2)
    }

    try {
      val result = reduce(0, chars.length)
      result._1 + result._2 == 0
    } catch {
      case _: AssertionError => false
    }
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
