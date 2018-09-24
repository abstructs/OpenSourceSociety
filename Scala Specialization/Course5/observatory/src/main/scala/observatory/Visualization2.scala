package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import Interaction.tileLocation
import Visualization.{interpolateColor, predictTemperature}

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 {

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
  ): Temperature = {
    d00 * (1 - point.x) * (1 - point.y) + d10 * point.x * (1 - point.y) + d01 * (1 - point.x) * point.y + d11 * point.x * point.y
  }

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */

  def visualizeGrid(
    grid: GridLocation => Temperature,
    colors: Iterable[(Temperature, Color)],
    tile: Tile
  ): Image = {

    val pixels = for(y <- tile.y until tile.y + 256; x <- tile.x until tile.x + 256)
    yield {
      val modX = x % 360
      val modY = y % 180

      GridLocation(x , 0)

      val d00 = grid(GridLocation(modX - 180, modY - 90))
      val d10 = grid(GridLocation(modX - 179, modY - 90))
      val d01 = grid(GridLocation(modX - 180, modY - 89))
      val d11 = grid(GridLocation(modX - 179, modY - 89))

      val temp = bilinearInterpolation(CellPoint(modX / (modX + 1), modY / (modY + 1)), d00, d01, d10, d11)
//      val t = tileLocation(Tile(x, y, tile.zoom))

//      val temp = grid(GridLocation(t.lat.round.toInt, t.lon.round.toInt))
      val color = interpolateColor(colors, temp)
      Pixel(color.red, color.green, color.blue, 127)
    }

//    pixels.foreach(println)


//    val ps = pixels.sortBy(_._1._1).sortBy(_._1._2).map(_._2)
    Image(256, 256, pixels.toArray)
  }
}
