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
      math.acos(
        math.sin(l1.lat) * math.sin(l2.lat) + math.cos(l1.lat) * math.cos(l2.lat) *
          math.cos(math.abs(l1.lon - l2.lon))
      )
    }

    def areAntipodes(l1: Location, l2: Location): Boolean = {
      l1.lat == -l2.lat && (l2.lon == l2.lon + 180 || l2.lon == l2.lon - 180)
    }

    // radius of the earth in km
    6371 * (if(l1 == l2) 0
    else if(areAntipodes(l1, l2)) math.Pi
    else deltaSigma(l1, l2))
  }

  def weight(distance: Double, p: Int): Double = {
    1 / math.pow(distance, p)
  }

  def interpolate(temps: Iterable[(Location, Temperature)], location: Location, pow: Int): Temperature = {
    temps.map(temp => {
      val w = weight(distance(temp._1, location), pow)
      (w * temp._2) / w
    }).sum
  }

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    temperatures.find(temp => distance(temp._1, location) < 1) match {
      case Some((_, temp)) => temp
      case None => interpolate(temperatures, location, 2)
    }
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolatex
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    def interpolateC(a: (Temperature, Double), b: (Temperature, Double), x: Temperature): Double = {
      a match { case (x0: Temperature, y0: Double) => b match {
          case (x1: Temperature, y1: Double) => (y0 * (x1 - x) + y1 * (x - x0)) / (x1 - x0)
        }
      }
    }

    val greaters = points.filter(_._1 >= value)
    val lowers = points.filter(_._1 <= value)

    val (x0, y0) = lowers.maxBy(_._1)
    val (x1, y1) = greaters.minBy(_._1)

    if(x0 == value) return y0
    if(x1 == value) return y1

    Color(
    interpolateC((x0, y0.red), (x1, y1.red), value).round.toInt,
    interpolateC((x0, y0.green), (x1, y1.green), value).round.toInt,
    interpolateC((x0, y0.blue), (x1, y1.blue), value).round.toInt)
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

