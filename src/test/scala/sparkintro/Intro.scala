package sparkintro

import java.util.UUID

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.joda.time.DateTime
import support.HandsOnSuite
import org.scalatest._

import scala.util.Random

object Utils {
  def createSparkContext = {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
      .set("spark.ui.port", "4040")
    new SparkContext(conf)
  }

  case class Bid(uid: String, price: Double, id: String, timestamp: Long)
  case class Display(uid: String, id: String, timestamp: Long)
  case class Click(uid: String, id: String, ctr: Double, timestamp: Long)

  def gen: (Bid, Option[Display], Option[Click]) = {
    val uid = UUID.randomUUID()
    val price = 0.002
    val id = Random.nextLong.toString
    val bts = DateTime.now
    val dts = bts.plusMillis(Random.nextInt(10000))
    val cts = dts.plusSeconds(Random.nextInt(240))

    val bid = Bid(uid.toString, price, id, bts.getMillis)

    val display = if(Random.nextInt(100) > 1) {
      Some(Display(uid.toString, id, dts.getMillis))
    } else {
      None
    }

    val click =
      if(display.isDefined && Random.nextInt(100) > 80) {
        Some(Click(uid.toString, id, Random.nextDouble(), dts.getMillis))
      } else {
        None
      }
    (bid, display, click)
  }

  lazy val (bidsList, displaysList, clicksList) = {
    val (bl,dlOpt,clOpt) = (1 to 100).map{ _ => gen}.unzip3
    (bl, dlOpt.flatten, clOpt.flatten)
  }
}

class Intro extends HandsOnSuite with BeforeAndAfter {
  import Utils.{Bid, Display, Click}

  private var sc: SparkContext = _

  before{
    sc = Utils.createSparkContext
  }

  after {
    if(sc != null){
      sc.stop
    }
  }

  def topNWords(input: RDD[String], n: Int): Array[String] = {
    ???
  }

  def topNWordsWithAtLeastMLetters(input: RDD[String], n: Int, m: Int): Array[String] = {
    ???
  }

  // Doc is here -> https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.package

  exercice("you can create RDD easily from a SparkContext") {
    val textRDD: RDD[String] = sc.textFile("lorem.txt")
    textRDD.count shouldEqual -1
  }

  exercice("you can do the same ") {
    val textRDD: RDD[String] = sc.textFile("lorem.txt")
    topNWords(textRDD, 10).toList shouldEqual List("an", "ex", "id", "cu", "ad", "te", "ut", "et", "ea", "no")
    topNWordsWithAtLeastMLetters(textRDD, 10, 5).toList shouldEqual List(
      "cotidieque", "oportere", "consectetuer", "adversarium", "posidonium", "philosophia", "verear", "democritum",
      "fierent", "eloquentiam"
    )
  }


  def innerJoin(bids: RDD[Bid], displays: RDD[Display], clicks: RDD[Click]): RDD[(Bid, Display, Click)] = {
    ???
  }

  exercice("implement a join") {
    import Utils._
    val bids: RDD[Bid] = sc.parallelize(bidsList)
    val displays: RDD[Display] = sc.parallelize(displaysList)
    val clicks: RDD[Click] = sc.parallelize(clicksList)

    innerJoin(bids, displays, clicks).collect should have size bidsList.size
  }

  def populateTables(sqlContext: SQLContext) {
    import sqlContext.implicits._
    import Utils._

    val bids = sc.parallelize[Bid](bidsList).toDF
    val displays = sc.parallelize[Display](displaysList).toDF
    val clicks = sc.parallelize[Click](clicksList).toDF

    bids.registerTempTable("bids")
    displays.registerTempTable("displays")
    clicks.registerTempTable("clicks")
  }

  def unregisterTables(sqlContext: SQLContext) {
    sqlContext.dropTempTable("bids")
    sqlContext.dropTempTable("displays")
    sqlContext.dropTempTable("clicks")
  }

  exercice("implement a join sql-ish") {
    import Utils._
    val sqlContext = new SQLContext(sc)
    populateTables(sqlContext)

    // What? I can do sql querries directly in spark? Too much power
    // https://spark.apache.org/docs/1.6.0/sql-programming-guide.html#inferring-the-schema-using-reflection
    sqlContext.sql(???).collect should have size clicksList.size

    unregisterTables(sqlContext)
  }

}
