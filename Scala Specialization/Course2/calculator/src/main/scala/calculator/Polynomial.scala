package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(math.pow(b(), 2) - (4 * a() * c()))
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    val pos = (-b() + math.sqrt(delta())) / (2 * a())
    val neg = (-b() - math.sqrt(delta())) / (2 * a())

    if(delta() < 0) Signal(Set())
    else Signal(Set(pos, neg))
  }
}
