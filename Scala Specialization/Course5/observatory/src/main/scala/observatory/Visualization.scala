package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import org.apache.commons.math3.util.FastMath

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  def distance(l1: Location, l2: Location): Double = {
    // distanced based on Great-circle distance: https://en.wikipedia.org/wiki/Great-circle_distance
    // Antipodes: https://en.wikipedia.org/wiki/Antipodes#Mathematical_description
    def deltaSigma(l1: Location, l2: Location): Double = {
      val toRadians = FastMath.PI / 180d

      FastMath.acos(
        FastMath.sin(l1.lat * toRadians) * FastMath.sin(l2.lat * toRadians) +
          FastMath.cos(l1.lat * toRadians) * FastMath.cos(l2.lat * toRadians) *
            FastMath.cos(FastMath.abs(l1.lon * toRadians - l2.lon * toRadians))
      )
    }

    def areAntipodes(l1: Location, l2: Location): Boolean = {
      l1.lat == -l2.lat && (l1.lon == l2.lon + 180d || l1.lon == l2.lon - 180d)
    }

    val earthsRadius = 6371

    earthsRadius * (if(l1 == l2) 0d
    else if(areAntipodes(l1, l2)) FastMath.PI
    else deltaSigma(l1, l2))
  }

  def weight(l1: Location, l2: Location, distance: Double): Double = {
    1d / FastMath.pow(distance, 6)
  }

  def inverseWeighting(temps: Iterable[(Location, Temperature)], location: Location): Temperature = {
    var hasLessThanOne = false
    var lessThanOneTemp = 0d
    var d = 0d

    val (numerator, denominator) = temps.par.takeWhile(temp => {
      d = distance(temp._1, location)
      if(d < 1 && !hasLessThanOne) hasLessThanOne = true; lessThanOneTemp = temp._2
      d >= 1
    }).aggregate((0d, 0d))((acc, temp) => {
      val w = weight(temp._1, location, distance(temp._1, location))
      (w * temp._2 + acc._1, w + acc._2)
    }, (t1, t2) => (t1._1 + t2._1, t1._2 + t2._2))

    if(hasLessThanOne) lessThanOneTemp
    else numerator / denominator
  }

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    inverseWeighting(temperatures, location)
//    temperatures.find(temp => distance(temp._1, location) < 1) match {
//      case Some((_, temp)) => temp
//      case None => inverseWeighting(temperatures, location)
//    }
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolatex
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    def interpolate(a: (Temperature, Double), b: (Temperature, Double), x: Temperature): Double = {
      a match { case (x0: Temperature, y0: Double) => b match {
          case (x1: Temperature, y1: Double) => (y0 * (x1 - x) + y1 * (x - x0)) / (x1 - x0)
        }
      }
    }

    val greaterThanValues = points.filter(_._1 >= value)
    val lowerThanValues = points.filter(_._1 <= value)

    if(greaterThanValues.isEmpty) points.maxBy(_._1)._2
    else if(lowerThanValues.isEmpty) points.minBy(_._1)._2
    else {
      val (x0, y0) = lowerThanValues.maxBy(_._1)
      val (x1, y1) = greaterThanValues.minBy(_._1)

      if(x0 == value) y0
      else if(x1 == value) y1
      else
        Color(
          interpolate((x0, y0.red), (x1, y1.red), value).round.toInt,
          interpolate((x0, y0.green), (x1, y1.green), value).round.toInt,
          interpolate((x0, y0.blue), (x1, y1.blue), value).round.toInt)
    }
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {
    val height = 180
    val width = 360

    def pixelIndexOfL(l: Location): Int = {
      (width * (-l.lat + 90) + l.lon + 180).toInt
    }

    def pixelIndexOfXY(x: Int, y: Int): Int = {
      width * y + x
    }

    def toLocation(x: Int, y: Int): Location = {
      Location(90 - y, -180 + x)
    }

    val pixels: Array[Pixel] = Array.fill(height * width){Pixel(0, 0, 0, 0)}

    temperatures.foreach(temp => {
      val c = interpolateColor(colors, temp._2)
      pixels(pixelIndexOfL(temp._1)) = Pixel(c.red, c.green, c.blue, 1)
    })

    for(h <- 0 until height; w <- 0 until width) {
      val p: Pixel = pixels(pixelIndexOfXY(w, h))
      if(p.alpha == 0) {
        val t: Temperature = predictTemperature(temperatures, toLocation(w, h))
        val c: Color = interpolateColor(colors, t)

        pixels(pixelIndexOfXY(w, h)) = Pixel(c.red, c.green, c.blue, 1)
      }
    }

    Image(width, height, pixels)
  }

}

