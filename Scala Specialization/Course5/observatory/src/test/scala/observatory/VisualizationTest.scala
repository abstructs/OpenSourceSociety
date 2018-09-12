package observatory


import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import Visualization._

trait VisualizationTest extends FunSuite with Checkers {

  test("interpolate should give reasonable values") {
    val result = interpolateColor(List((60, Color(255, 255, 255)), (-60, Color(0, 0, 0))) , 60d)
    fail(s"Result was: ${result}")
  }

}
