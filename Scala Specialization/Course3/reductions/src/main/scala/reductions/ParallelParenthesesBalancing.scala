package reductions

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

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): Boolean = {
      if(math.ceil((until - idx) / 2) < 1) {
        if(chars(until - 1) == '(') true
        else if (chars(until - 1) == ')') false
        else throw new Error("oops")
      } else {
        val mid = math.ceil((until - idx) / 2).toInt

        chars(idx) == '('
        chars(idx) == ')'

        chars(mid) == '('
        chars(mid) == ')'

        val (x, y) = parallel(traverse(idx, mid, arg1, arg2), traverse(mid, until, arg1, arg2))
        x && y
      }
    }

    def reduce(from: Int, until: Int) /* :??? */ = {
      traverse(from, until, 0, 0)
    }
    
    if(chars.length == 0) true
    else reduce(0, chars.length) // == ???
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
