
import common._

import scalashop.RGBA

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {

    if(radius <= 0) return src(x, y)


    var (rs, gs, bs, as) = (List[RGBA](), List[RGBA](), List[RGBA](), List[RGBA]())

    var i = x - radius

    while(i <= x + radius) {
      var j = y - radius

      while (j <= y + radius) {
//        && !(i == x && j == y)
        if(i >= 0 && j >= 0 && i < src.width && j < src.height) {
          val rgba = src(i, j)
          val (r, g, b, a) = (red(rgba), green(rgba), blue(rgba), alpha(rgba))
          rs = r :: rs
          gs = g :: gs
          bs = b :: bs
          as = a :: as
        }

        j += 1
      }


      i += 1
    }
    val (rsAvg, gsAvg, bsAvg, asAvg) = (rs.sum / rs.length, gs.sum / gs.length, bs.sum / bs.length, as.sum / as.length)
//    val (r, g, b, a) = (red(src(x, y)), green(src(x, y)), blue(src(x, y)), alpha(src(x, y)))

    rgba(rsAvg, gsAvg, bsAvg, asAvg)
  }

}
