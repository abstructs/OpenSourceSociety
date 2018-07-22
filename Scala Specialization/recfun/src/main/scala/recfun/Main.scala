package recfun

import math.abs

object Main {
  def main(args: Array[String]) {
//    println("Pascal's Triangle")
//    for (row <- 0 to 10) {
//      for (col <- 0 to row)
//        print(pascal(col, row) + " ")
//      println()
//    }
//    println(factorial(product)(6))
//    println(acc(product)(1, 6, 1))
//
//    println(sum(x => x)(1,6))
//    println(acc(add)(1, 6, 0))
    println(sqrt(4))

  }

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + sum(f)(a + 1, b)
  }

  def product(a: Int, b: Int): Int = a * b
  def add(a: Int, b: Int): Int = a + b

  def factorial(f: (Int, Int) => Int)(n: Int): Int = {
    if(n == 0) 1
    else f(n, factorial(f)(n - 1))
  }

  val tolerance = 0.0001
  def isCloseEnough(x: Double, y: Double): Boolean =
    abs((x - y) / x) / x < tolerance

  def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
    def iterate(guess: Double): Double = {
      val next = f(guess)
      if(isCloseEnough(guess, next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }
  fixedPoint(x => 1 + x/2)(1)

  def averageDamp(f: Double => Double)(x: Double): Double = (x + f(x)) / 2

  def sqrt(x: Double): Double = {
    fixedPoint(averageDamp(x => x))(1)
  }




  def acc(f: (Int, Int) => Int)(a: Int, b: Int, base: Int): Int = {
    if (a > b) base
    else f(a, acc(f)(a + 1, b, base))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(c == 0 || c == r) return 1
      pascal(c, r - 1) + pascal(c - 1, r - 1)
    }

  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def aux(xs: List[Char], paraCache: List[Char]): Boolean = {
        if(xs.isEmpty) paraCache.isEmpty
        else if(xs.head == '(') aux(xs.tail, '(' :: paraCache)
        else if(xs.head == ')') {
          if(paraCache.isEmpty) false
          else aux(xs.tail, paraCache.tail)
        }
        else aux(xs.tail, paraCache)
      }

      aux(chars, List())
    }

  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(coins.isEmpty || money < 0) return 0
      if(money == 0) return 1
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }
