package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
//   test("string take") {
//     val message = "hello, world"
//     assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val negS1 = singletonSet(-1)
    val negS2 = singletonSet(-2)
    val negS3 = singletonSet(-3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val u = union(s1, s2) // 1, 2
      val uu = union(union(s1, s2), s3) // 1, 2, 3
      assert(contains(u, 1))
      assert(contains(u, 2))
      assert(!contains(u, 3))
      assert(contains(uu, 1), "Union 1")
      assert(contains(uu, 2), "Union 2")
      assert(contains(uu, 3), "Union 3")
      assert(!contains(uu, 4), "Union 4")
    }
  }

  test("intersection contains intersecting elements of each set") {
    new TestSets {
      val s = union(union(s1, s2), s3) // 1, 2, 3
      val i = intersect(s, s1) // 1
      assert(contains(i, 1), "Intersect 1")
      assert(!contains(i, 2), "Intersect 2")
    }
  }

  test("diff contains the difference of elements of each set") {
    new TestSets {
      val s = union(s1, s2) // 1, 2
      val d: Set = diff(s3, s) // 3

      assert(!contains(d, 1), "Diff 1")

      assert(!contains(d, 2), "Diff 2")
      assert(contains(d, 3), "Diff 3")
    }
  }

  test("filter contains the subset of elements") {
    new TestSets {

      val s = union(union(negS1, negS2), s3) // -1, -2, 3
      val p = (elem: Int) => elem > 0
      val f = filter(s, p) // 3

      assert(!contains(f, -1), "Filter -1")
      assert(!contains(f, -2), "Filter -2")
      assert(contains(f, 3), "Filter 3")
    }
  }

  test("forall checks if every element in set s satisfies p") {
    new TestSets {
      val negSet = union(union(negS1, negS2), negS3) // -1, -2, -3
      val posSet = union(union(s1, s2), s3) // 1, 2, 3
      val fulSet = union(negSet, posSet) // -1, -2, -3, 1, 2, 3

      val isPositive = (elem: Int) => elem >= 0
      val isNegative = (elem: Int) => elem < 0

      assert(forall(posSet, isPositive), "Forall posSet isPositive")
      assert(forall(negSet, isNegative), "Forall negSet isNegative")
      assert(!forall(negSet, isPositive), "Forall negSet isPositive")
      assert(!forall(posSet, isNegative), "Forall posSet isNegative")
      assert(!forall(fulSet, isPositive), "Forall fulSet isPositive")
      assert(!forall(fulSet, isNegative), "Forall fulSet isNegative")
    }
  }

  test("exists checks if any element in set s satisfies p") {
    new TestSets {
      val negSet = union(union(negS1, negS2), negS3) // -1, -2, -3
      val posSet = union(union(s1, s2), s3) // 1, 2, 3
      val fulSet = union(negSet, posSet) // -1, -2, -3, 1, 2, 3

      val aPositive = (elem: Int) => elem >= 0
      val aNegative = (elem: Int) => elem < 0

      assert(!exists(posSet, aNegative), "Forall posSet aNegative")
      assert(!forall(negSet, aPositive), "Forall fulSet aPositive")
      assert(exists(fulSet, aPositive), "Forall fulSet aPositive")
      assert(exists(fulSet, aNegative), "Forall negSet aNegative")
    }
  }

  test("map transforms each element in a set") {
    new TestSets {
      val posSet = union(union(s1, s2), s3) // 1, 2, 3
      val addOne = (elem: Int) => elem + 1
      val mapped = map(posSet, addOne) // 2, 3, 4

      assert(contains(mapped, 2) &&
        contains(mapped, 3) &&
        contains(mapped, 4) &&
        !contains(mapped, 1), "map posSet addOne")
    }
  }


}
