package barneshut

import java.util.concurrent._
import scala.collection._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common._
import scala.math._
import scala.collection.parallel._
import barneshut.conctrees.ConcBuffer

@RunWith(classOf[JUnitRunner])
class BarnesHutSuite extends FunSuite {

  // test cases for quad tree

import FloatOps._
  test("Empty: center of mass should be the center of the cell") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.massX == 51f, s"${quad.massX} should be 51f")
    assert(quad.massY == 46.3f, s"${quad.massY} should be 46.3f")
  }

  test("Empty: mass should be 0") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.mass == 0f, s"${quad.mass} should be 0f")
  }

  test("Empty: total should be 0") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.total == 0, s"${quad.total} should be 0")
  }

  test("Leaf with 1 body") {
    val b = new Body(123f, 18f, 26f, 0f, 0f)
    val quad = Leaf(17.5f, 27.5f, 5f, Seq(b))

    assert(quad.mass ~= 123f, s"${quad.mass} should be 123f")
    assert(quad.massX ~= 18f, s"${quad.massX} should be 18f")
    assert(quad.massY ~= 26f, s"${quad.massY} should be 26f")
    assert(quad.total == 1, s"${quad.total} should be 1")
  }


  test("Fork with 3 empty quadrants and 1 leaf (nw)") {
    val b = new Body(123f, 18f, 26f, 0f, 0f)
    val nw = Leaf(17.5f, 27.5f, 5f, Seq(b))
    val ne = Empty(22.5f, 27.5f, 5f)
    val sw = Empty(17.5f, 32.5f, 5f)
    val se = Empty(22.5f, 32.5f, 5f)
    val quad = Fork(nw, ne, sw, se)

    assert(quad.centerX == 20f, s"${quad.centerX} should be 20f")
    assert(quad.centerY == 30f, s"${quad.centerY} should be 30f")
    assert(quad.mass ~= 123f, s"${quad.mass} should be 123f")
    assert(quad.massX ~= 18f, s"${quad.massX} should be 18f")
    assert(quad.massY ~= 26f, s"${quad.massY} should be 26f")
    assert(quad.total == 1, s"${quad.total} should be 1")
  }

  test("Empty.insert(b) should return a Leaf with only that body") {
    val quad = Empty(51f, 46.3f, 5f)
    val b = new Body(3f, 54f, 46f, 0f, 0f)
    val inserted = quad.insert(b)
    inserted match {
      case Leaf(centerX, centerY, size, bodies) =>
        assert(centerX == 51f, s"$centerX should be 51f")
        assert(centerY == 46.3f, s"$centerY should be 46.3f")
        assert(size == 5f, s"$size should be 5f")
        assert(bodies == Seq(b), s"$bodies should contain only the inserted body")
      case _ =>
        fail(s"Empty.insert() should have returned a Leaf, was $inserted")
    }
  }

  test("Fork.insert(b) should insert into nw of Fork") {
    val nw = Empty(17.5f, 27.5f, 5f)
    val ne = Empty(22.5f, 27.5f, 5f)
    val sw = Empty(17.5f, 32.5f, 5f)
    val se = Empty(22.5f, 32.5f, 5f)

    val quad = Fork(nw, ne, sw, se)

    val b = new Body(3f, 5, 5, 0f, 0f)
    val inserted = quad.insert(b)
    inserted match {
      case Fork(nw, ne, sw, se) =>
        nw match {
          case Leaf(_, _, _, bodies) => assert(bodies.contains(b), s"leaf should contain $b, bodies was $bodies")
          case _ => fail(s"nw should have returned a Leaf, was $nw\nnw: $nw ne: $ne sw: $sw se: $se")
        }
        assert(nw.total == 1, s"total should be 1, was ${nw.total}")
      case _ =>
        fail(s"Fork.insert() should have returned a Fork, was $inserted")
    }
  }
//  Leaf(1.0,1.0,1.0E-5,List(barneshut.package$Body@3427b02d, barneshut.package$Body@647e447))
//
//  Leaf(1.0,1.0,1.00001,List(barneshut.package$Body@647e447, barneshut.package$Body@3427b02d))


//  test("Leaf.insert(b).insert(b).insert(b).insert(b) should have all the bodies") {
//    val b = new Body(3f, 54f, 46f, 0f, 0f)
//    val quad = Leaf(51f, 46.3f, 5f, Seq())
//    val inserted = quad.insert(b).insert(b).insert(b).insert(b)
//    inserted match {
//      case Leaf(_, _, size, bodies) =>
//        assert(size == 4, s"$size should have a size of 4")
//        assert(bodies == Seq(b, b, b, b), s"$bodies should contain only the inserted body")
//      case _ =>
//        fail(s"Leaf.insert().insert().insert().insert() should have returned 4 bodies, was $inserted")
//    }
//  }

  test("Leaf.insert() when leaf.size > minimumSize should return a Fork()") {
    val b = new Body(3f, 54f, 46f, 0f, 0f)
    val quad = Leaf(51f, 46.3f, 5f, Seq())
    val inserted = quad.insert(b).insert(b).insert(b).insert(b)
    inserted match {
      case Fork(_, _, _, _) => {
        println("success")
      }
      case Leaf(_, _, size, bodies) =>
        println("fail")
        fail(s"Leaf.insert() should have returned a Fork, was $inserted")
//        assert(size == 4, s"$size should have a size of 4")
//        assert(bodies == Seq(b, b, b, b), s"$bodies should contain only the inserted body")
      case _ =>
        fail(s"Leaf.insert() should have returned a Fork, was $inserted")
    }
  }

//  test("Leaf.insert() when leaf.size > minimumSize should return a Fork()") {
//    val b = new Body(3f, 54f, 46f, 0f, 0f)
//    val quad = Leaf(51f, 46.3f, 5f, Seq())
//    val inserted = quad.insert(b).insert(b).insert(b).insert(b)
//    inserted match {
//      case Fork(_, _, _, _) => {
//        println("success")
//      }
//      case Leaf(_, _, size, bodies) =>
//        println("fail")
//        fail(s"Leaf.insert() should have returned a Fork, was $inserted")
//      //        assert(size == 4, s"$size should have a size of 4")
//      //        assert(bodies == Seq(b, b, b, b), s"$bodies should contain only the inserted body")
//      case _ =>
//        fail(s"Leaf.insert() should have returned a Fork, was $inserted")
//    }
//  }

  // test cases for Body

  test("Body.updated should do nothing for Empty quad trees") {
    val b1 = new Body(123f, 18f, 26f, 0f, 0f)
    val body = b1.updated(Empty(50f, 60f, 5f))

    assert(body.xspeed == 0f)
    assert(body.yspeed == 0f)
  }

  test("Body.updated should take bodies in a Leaf into account") {
    val b1 = new Body(123f, 18f, 26f, 0f, 0f)
    val b2 = new Body(524.5f, 24.5f, 25.5f, 0f, 0f)
    val b3 = new Body(245f, 22.4f, 41f, 0f, 0f)

    val quad = Leaf(15f, 30f, 20f, Seq(b2, b3))

    val body = b1.updated(quad)

    assert(body.xspeed ~= 12.587037f)
    assert(body.yspeed ~= 0.015557117f)
  }

  // test cases for sector matrix

  test("'SectorMatrix.+=' should add a body at (25,47) to the correct bucket of a sector matrix of size 96") {
    val body = new Body(5, 25, 47, 0.1f, 0.1f)
    val boundaries = new Boundaries()
    boundaries.minX = 1
    boundaries.minY = 1
    boundaries.maxX = 97
    boundaries.maxY = 97
    val sm = new SectorMatrix(boundaries, SECTOR_PRECISION)
    sm += body
    val res = sm(2, 3).size == 1 && sm(2, 3).find(_ == body).isDefined
    assert(res, s"Body not found in the right sector")
  }

}

object FloatOps {
  private val precisionThreshold = 1e-4

  /** Floating comparison: assert(float ~= 1.7f). */
  implicit class FloatOps(val self: Float) extends AnyVal {
    def ~=(that: Float): Boolean =
      abs(self - that) < precisionThreshold
  }

  /** Long floating comparison: assert(double ~= 1.7). */
  implicit class DoubleOps(val self: Double) extends AnyVal {
    def ~=(that: Double): Boolean =
      abs(self - that) < precisionThreshold
  }

  /** Floating sequences comparison: assert(floatSeq ~= Seq(0.5f, 1.7f). */
  implicit class FloatSequenceOps(val self: Seq[Float]) extends AnyVal {
    def ~=(that: Seq[Float]): Boolean =
      self.size == that.size &&
        self.zip(that).forall { case (a, b) =>
          abs(a - b) < precisionThreshold
        }
  }
}

