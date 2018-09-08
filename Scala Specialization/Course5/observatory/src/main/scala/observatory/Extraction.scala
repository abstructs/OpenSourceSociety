package observatory

import java.io.InputStream
import java.net.URL
import java.time.LocalDate

import org.apache.spark.sql._
import org.apache.spark.sql.streaming.DataStreamReader
import org.apache.spark.sql.types.StructType

import scala.io.Source


/**
  * 1st milestone: data extraction
  */
object Extraction {

  def main(args: Array[String]): Unit = {
    locateTemperatures(1975, "stations.csv", "1975.csv")
  }

  case class Temperature(stn: String, wban: String, month: Int, day: Int, temperature: Double)

  case class Station(stn: String, wban: String, latitude: Double, longitude: Double)

  def tempSchema: StructType = Encoders.product[Temperature].schema

  def stationSchema: StructType = Encoders.product[Station].schema

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {



    val ss = SparkSession.builder()
      .master("local")
      .appName("temps")
      .getOrCreate()

    val temps = ss.read.schema(tempSchema).csv(s"src/main/resources/$temperaturesFile")
    val stations = ss.read.schema(stationSchema).csv(s"src/main/resources/$stationsFile")

    temps.show(5)

//    ss.Da
//    val s = new DataStreamReader(ss)
//    println(s)
//    s.show(2)


//    val tempFile: InputStream = getClass.getResourceAsStream(temperaturesFile)
//    val stationFile: InputStream = getClass.getResourceAsStream(stationsFile)


    List()
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    ???
  }

}
