package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Visualization._

import scala.collection.immutable
import scala.collection.parallel.ParSeq

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */

  def tileLocation(tile: Tile): Location = {
    val lat = Math.atan(Math.sinh(Math.PI - tile.y / Math.pow(2, tile.zoom) * 2 * Math.PI)) * 180 / Math.PI
    val lon = tile.x / Math.pow(2, tile.zoom) * 360 - 180

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
    def generatePixels(tile: Tile): Array[((Int, Int), Pixel)] = {
      if(Math.pow(2, tile.zoom) >= 256) {
        val loc = tileLocation(tile)
        val t = predictTemperature(temperatures, loc)
        val c = interpolateColor(colors, t)

        Array(((tile.x, tile.y), Pixel(c.red, c.green, c.blue, 127)))
      } else {
        // TODO: Run this in parallel
        val nw = generatePixels(Tile(2 * tile.x, 2 * tile.y, tile.zoom + 1))
        val ne = generatePixels(Tile(2 * tile.x + 1, 2 * tile.y, tile.zoom + 1))
        val sw = generatePixels(Tile(2 * tile.x, 2 * tile.y + 1, tile.zoom + 1))
        val se = generatePixels(Tile(2 * tile.x + 1, 2 * tile.y + 1, tile.zoom + 1))

        ParSeq(nw, ne, sw, se).reduce(_ ++ _)
      }
    }
    val pixels = generatePixels(tile)

    // sort by the columns then sort the rows
    val ps: Array[Pixel] = pixels.sortBy(_._1._1).sortBy(_._1._2).map(_._2)

//    ps.foreach(println)
//    pixels.sort

    Image(256, 256, ps)
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
      for(zoom <- 0 to 3; x <- 0 until Math.pow(2, zoom).toInt; y <- 0 until Math.pow(2, zoom).toInt) {
        generateImage(data._1, Tile(x, y, zoom), data._2)
      }
    })
//    ???
  }
}
