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
    d00 * (1d - point.x) * (1d - point.y) + d10 * point.x * (1d - point.y) + d01 * (1d - point.x) * point.y + d11 * point.x * point.y
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
//    val height = 256
//    val width = 256
//    val offsetX = tile.x
//    val offsetY = tile.y
//
//    val pixels: Array[Pixel] = Array.fill(height * width){Pixel(0, 0, 0, 0)}
//
//    for(x <- 0 until 256; y <- 0 until 256) {
//      val loc = tileLocation(Tile(x + offsetX, y + offsetY, tile.zoom + 8))
//
//      val d00 = GridLocation(loc.lat.floor.toInt, loc.lon.floor.toInt)
//      val d01 = GridLocation(loc.lat.floor.toInt, loc.lon.floor.toInt)
//      val d10 = GridLocation(loc.lat.floor.toInt, loc.lon.floor.toInt)
//      val d11 = GridLocation(loc.lat.floor.toInt, loc.lon.floor.toInt)
//
//      bilinearInterpolation(CellPoint(loc.lon - loc.lon.floor, loc.lat - loc.lat.floor), ), )
//
//
//      pixels(y * width + x) = Pixel(0, 0, 0, 0)
//    }
//
//    Image(256, 256, pixels)


//    val pixels = for(y <- 0 until 256; x <- 0 until 256)
//    yield {
//      val pixelLoc = tileLocation(Tile(x + 256 * tile.x, y + 256 * tile.y, tile.zoom + 8))
//
//      val d00 = grid(GridLocation(pixelLoc.lat.toInt, pixelLoc.lon.toInt))
//      val d01 = grid(GridLocation(pixelLoc.lat.toInt + 1, pixelLoc.lon.toInt))
//      val d10 = grid(GridLocation(pixelLoc.lat.toInt, pixelLoc.lon.toInt + 1))
//      val d11 = grid(GridLocation(pixelLoc.lat.toInt + 1, pixelLoc.lon.toInt + 1))
//
//      val temp = bilinearInterpolation(CellPoint(pixelLoc.lon - pixelLoc.lon.floor, pixelLoc.lat - pixelLoc.lat.floor), d00, d10, d01, d11)
//      val c = interpolateColor(colors, temp)
//
//      Pixel(c.red, c.green, c.blue, 127)
//    }
//
//    Image(256, 256, pixels.toArray)
    ???
  }
}
