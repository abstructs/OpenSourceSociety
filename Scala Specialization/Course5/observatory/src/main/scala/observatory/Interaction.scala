package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */

  def toLocation(x: Double, y: Double): Location = {
    Location(90 - y, -180 + x)
  }

  def tileLocation(tile: Tile): Location = {
    // TODO: Implement this properly
    // based off https://en.wikipedia.org/wiki/Web_Mercator_projection

    val location = toLocation(tile.x, tile.y)
    val (lat, lon) = (location.lat, location.lon)

    val x = 256 / 2 * Math.PI * Math.pow(2, tile.zoom) * (lon + Math.PI)
    val y = 256 / 2 * Math.PI * Math.pow(2, tile.zoom) * (Math.PI - Math.log(Math.tan(Math.PI / 4 + lat / 2)))

    toLocation(x, y)


  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): Image = {
    ???
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
    ???
  }

}
