package observatory

import java.io.File

import com.sksamuel.scrimage.{Image, Pixel}
import monix.eval.Task
import monix.execution.CancelableFuture
import observatory.Visualization._
import observatory.Extraction._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import monix.execution.Scheduler.Implicits.global
import org.apache.commons.math3.util.FastMath

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */

  def tileLocation(tile: Tile): Location = {
    val lon = tile.x / FastMath.pow(2, tile.zoom) * 360 - 180
    val lat = FastMath.atan(FastMath.sinh(FastMath.PI - tile.y / FastMath.pow(2, tile.zoom) * 2 * FastMath.PI)) * 180 / FastMath.PI

    Location(lat, lon)
  }

  /**
    *
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): Image = {
    val z = tile.zoom
    def generatePixels(tile: Tile): Array[((Int, Int), Pixel)] = {

      if(FastMath.pow(2, tile.zoom) == FastMath.pow(2, 8 + z)) {
        val loc = tileLocation(tile)
        val t = predictTemperature(temperatures, loc)
        val c = interpolateColor(colors, t)

        Array(((tile.x, tile.y), Pixel(c.red, c.green, c.blue, 127)))
      } else {
        val nw = Task(generatePixels(Tile(2 * tile.x, 2 * tile.y, tile.zoom + 1)))
        val ne = Task(generatePixels(Tile(2 * tile.x + 1, 2 * tile.y, tile.zoom + 1)))
        val sw = Task(generatePixels(Tile(2 * tile.x, 2 * tile.y + 1, tile.zoom + 1)))
        val se = Task(generatePixels(Tile(2 * tile.x + 1, 2 * tile.y + 1, tile.zoom + 1)))

        val tasks = Task.gatherUnordered(Seq(nw, ne, sw, se)).runAsync

        Await.result[List[Array[((Year, Year), Pixel)]]](tasks, Duration.Inf).reduce(_ ++ _)

      }
    }

    val pixels = generatePixels(tile)

    // sort by the columns then sort the rows
    val ps = pixels.sortBy(_._1._1).sortBy(_._1._2)

//    ps.foreach(println)

    Image(256, 256, ps.map(_._2))
  }

  def generateImage(year: Year, t: Tile, data: Iterable[(Location, Temperature)]): Unit = {
    val colors = List(
      (60d, Color(255, 255, 255)),
      (32d, Color(255, 0, 0)),
      (12d, Color(255, 255, 0)),
      (0d, Color(0, 255, 255)),
      (-15d, Color(0, 0, 255)),
      (-27d, Color(255, 0, 255)),
      (-50d, Color(33, 0, 255)),
      (-60d, Color(0, 0, 0)))


    println("Creating image from data...")

    val image = tile(data, colors, t)

    println("Writing image to disk...")

    image.output(new File("."))
  }

  def main(args: Array[String]): Unit = {
//    2015
    val yearlyData = (1975 to 1975).map(year => {
      val records = locateTemperatures(year, "/stations.csv", s"/$year.csv")
      val data: Iterable[(Location, Temperature)] = locationYearlyAverageRecords(records)
      (year, data)
    })

    generateTiles[Iterable[(Location, Temperature)]](yearlyData, generateImage)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Year, Data)],
    generateImage: (Year, Tile, Data) => Unit
  ): Unit = {
    yearlyData.foreach(data => {
      // 3
      for(zoom <- 0 to 3; x <- 0 until FastMath.pow(2, zoom).toInt; y <- 0 until FastMath.pow(2, zoom).toInt) {
        generateImage(data._1, Tile(x, y, zoom), data._2)
      }
    })
  }
}
