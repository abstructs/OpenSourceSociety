def abs(x:Double) = if (x < 0) -x else x

def sqrtIter(guess: Double, x: Double): Double =
  if (isGoodEnough(guess, x)) guess
  else sqrtIter(improve(guess, x), x)

def isGoodEnough(guess: Double, x: Double) = {
  abs(guess * guess - x ) / x < 0.0001
}

def improve(guess: Double, x: Double) = (guess + x / guess) / 2

def sqrt(x: Double) = sqrtIter(1.0, x)

def factorial(n: Double) = {
  def fact(n: Double, acc: Double): Double = {
    if (n == 0) return acc
    fact(n - 1, n * acc)
  }
  fact(n, 1)
}

factorial(6)

println(factorial(2))



















