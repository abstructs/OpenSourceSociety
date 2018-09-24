package observatory

import observatory.Interaction.tile
import Visualization2._
import Manipulation._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

trait Visualization2Test extends FunSuite with Checkers {

  test("visualizeGrid should compile") {
    val temps = List(28d, 19d, 14d)
    val locs = List(Location(38.8951, -77.0364), Location(43.6532, 79.3832), Location(45.5017, 73.5673))
    val ts = locs.zip(temps)
    val cs = List((-30d, Color(0, 0, 0)), (30d, Color(255, 255, 255)))
    val tile = Tile(0, 0, 0)

    visualizeGrid(makeGrid(ts), cs, tile)
  }
}
