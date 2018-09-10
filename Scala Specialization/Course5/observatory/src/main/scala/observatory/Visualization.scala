package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  def distance(l1: Location, l2: Location): Double = {
    // distanced based on Great-circle distance: https://en.wikipedia.org/wiki/Great-circle_distance
    // Antipodes: https://en.wikipedia.org/wiki/Antipodes#Mathematical_description
    def deltaSigma(l1: Location, l2: Location): Double = {
      1 / math.cos(
        math.sin(l1.lat) * math.sin(l2.lat) + math.cos(l1.lon) * math.cos(l2.lon) *
          math.cos(math.abs(l1.lat - l2.lat) * math.abs(l1.lon * l2.lon))
      )
    }

    def areAntipodes(l1: Location, l2: Location): Boolean = {
      l1.lat == -l2.lat && (l2.lon == l2.lon + 180 || l2.lon == l2.lon - 180)
    }

    // radius of the earth in km
    6371 * (if(l1 == l2) 0
    else if(areAntipodes(l1, l2)) math.Pi
    else  deltaSigma(l1, l2))
  }

  def weight(l1: Location, l2: Location, p: Int): Double = {
    1 / math.pow(distance(l1, l2), p)
  }

  def interpolate(temp: (Location, Temperature), location: Location, pow: Int): Double = {
    weight(temp._1, location, pow) * temp._2
  }

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    temperatures.map(temp => interpolate(temp, location, 2)).sum
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolatex
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    def interpolate(a: (Temperature, Double), b: (Temperature, Double), x: Temperature) = {
      a match { case (x0: Temperature, y0: Double) => b match {
          case (x1: Temperature, y1: Double) => (y0 * (x1 - x) + y1 * (x - x0)) / (x1 - x0)
        }
      }
    }

    val sortedPoints = points.toArray.sortWith((p1, p2) => p1._1 > p2._1)
    val upperIndex = sortedPoints.indexWhere(_._1 > value)
    val lowerIndex = upperIndex - 1

    val upper = points.head
    val lower = points.tail.head






    // idea: Compute the interpolations between two colours on a 1D plane and weigh the bounds by the ratio of the bound to
    // the value
    // ex:  red1 * distance(value, red1)/distance(value, red2) - red2 * distance(value, red2)/distance(value, red1)
    ???
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {
    ???
  }

}
