package observatory

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import Interaction.{tile, _}

import scala.collection.concurrent.TrieMap

trait InteractionTest extends FunSuite with Checkers {
  test("tile should compile and runtime check") {
    val temps = List(28d, 19d, 14d)
    val locs = List(Location(38.8951, -77.0364), Location(43.6532, 79.3832), Location(45.5017, 73.5673))
    val ts = locs.zip(temps)
    val cs = List((-30d, Color(0, 0, 0)), (30d, Color(255, 255, 255)))

//    tile(ts, cs, tile = Tile(0, 0, 0))

    println(tile(ts, cs, tile = Tile(0, 0, 0)).pixel((0, 5)))
    println(tile(ts, cs, tile = Tile(0, 0, 1)).pixel((0, 5)))


//    fail(s"Result was: ${result}")
  }
}
