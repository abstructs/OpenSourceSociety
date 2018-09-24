package observatory

import scala.collection.mutable
import Visualization.predictTemperature
/**
  * 4th milestone: value-added information
  */
object Manipulation {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature = {
    val memo: mutable.Map[GridLocation, Temperature] = mutable.Map()

    (location: GridLocation) => {
      memo get location match {
        case Some(temp: Temperature) => temp
        case None => {
          val temp = predictTemperature(temperatures, Location(location.lat, location.lon))
          memo += (location -> temp)

          temp
        }
      }
    }
  }

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
  def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature = {
//    val memo: mutable.Map[GridLocation, Temperature] = mutable.Map()
//
    (location: GridLocation) => {
      2d
//      memo get location match {
//        case Some(avg: Temperature) => avg
//        case None => {
//          val avg = temperaturess.par.aggregate(0d)((acc, temperatures) => {
//            acc + predictTemperature(temperatures, Location(location.lat, location.lon))
//          }, _ + _) / temperaturess.size
//
//          memo += (location -> avg)
//
//          avg
//        }
      }
//    }
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature = {
    ???
//    val memo: mutable.Map[GridLocation, Temperature] = mutable.Map()
//
    (location: GridLocation) => {
//      memo get location match {
//        case Some(devi: Temperature) => devi
//        case None => {
//          val devi = normals(location) - predictTemperature(temperatures, Location(location.lat, location.lon))
//
//          memo += (location -> devi)
//
//          devi
//        }
//      }
      2d
    }
  }


}

