package observatory

import java.io.InputStream
import java.net.URL
import java.nio.file.Paths
import java.time.LocalDate

//import org.apache.spark
//import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

/**
  * 1st milestone: data extraction
  */
object Extraction {
  val ss: SparkSession = SparkSession.builder()
    .master("local")
    .appName("temps")
    .getOrCreate()

  import ss.implicits._

  def main(args: Array[String]): Unit = {
//    val records =

    locationYearlyAverageRecords(locateTemperatures(1975, "/stations.csv", "/1975.csv"))
  }

  case class TempuratureTypes(stn: Int, wban: Int, month: Int, day: Int, temperature: Temperature)

  case class Station(stn: Int, wban: Int, latitude: Double, longitude: Double)

  def tempSchema: StructType = Encoders.product[TempuratureTypes].schema

  def stationSchema: StructType = Encoders.product[Station].schema

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    val temps = ss.read.schema(tempSchema).csv(Paths.get(getClass.getResource(temperaturesFile).toURI).toString)
    val stations = ss.read.schema(stationSchema).csv(Paths.get(getClass.getResource(stationsFile).toURI).toString)
      .filter($"longitude".isNotNull && $"latitude".isNotNull)

    temps
      .join(stations,
        temps("stn") <=> stations("stn") && temps("wban") <=> stations("wban"))
      .select($"month", $"day", $"longitude", $"latitude", (($"temperature" - 32) * 5 / 9).as("temperature"))
      .collect()
      .map { row =>
        (LocalDate.of(year, row.getAs[Int]("month"), row.getAs[Int]("day")),
          Location(row.getAs[Double]("latitude"), row.getAs[Double]("longitude")),
          row.getAs[Temperature]("temperature"))
      }
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    def avgTemp(records: Iterable[(LocalDate, Location, Temperature)]): Temperature = {
      records.reduce((acc, record) => (acc._1, acc._2, acc._3 + record._3))._3 / records.size
    }

    records.groupBy(_._2:Location)
      .map { case (location, rs) => (location, avgTemp(rs)) }
  }
}
