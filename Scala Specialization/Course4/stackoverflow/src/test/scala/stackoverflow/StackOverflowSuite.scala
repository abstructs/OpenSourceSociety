package stackoverflow

import org.scalatest.{FunSuite, BeforeAndAfterAll}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import StackOverflow._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import java.io.File

@RunWith(classOf[JUnitRunner])
class StackOverflowSuite extends FunSuite with BeforeAndAfterAll {


  lazy val testObject = new StackOverflow {
    override val langs =
      List(
        "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
        "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")
    override def langSpread = 50000
    override def kmeansKernels = 45
    override def kmeansEta: Double = 20.0D
    override def kmeansMaxIterations = 120
  }

  val lines: RDD[String] = sc.textFile("src/main/resources/stackoverflow/stackoverflow.csv")
  val raw: RDD[Posting] = rawPostings(lines)
  val grouped: RDD[(QID, Iterable[(Question, Answer)])] = groupedPostings(raw)
  val scored: RDD[(Question, HighScore)] = scoredPostings(grouped)
  val vectors: RDD[(LangIndex, HighScore)] = vectorPostings(scored)


  test("testObject can be instantiated") {
    val instantiatable = try {
      testObject
      true
    } catch {
      case _: Throwable => false
    }
    assert(instantiatable, "Can't instantiate a StackOverflow object")
  }

  test("scoredPostings should have 2121822 entries") {
    val scoredCount = scored.count()
    assert(scored.count() == 2121822, s"scored count should be 2121822, was $scoredCount")
  }

  test("groupPostings should have 2121822 entries") {
    val groupedCount = grouped.count()
    assert(groupedCount == 2121822, s"scored count should be 2121822, was $groupedCount")
  }

  test("vectorPostings should have 2121822 entries") {
    val vectorCount = vectors.count()
    assert(vectorCount == 2121822, s"scored count should be 2121822, was $vectorCount")
  }

  // TODO: write better tests for vectorPostings

  test("vectorPostings should work with sampleVectors") {
    sampleVectors(vectors)
  }

}
