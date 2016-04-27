package scaldingintro

import java.util.UUID

import cascading.pipe.CoGroup
import cascading.tuple.{Tuple, TupleEntry}
import com.twitter.scalding._
import com.twitter.scalding.source.TypedSequenceFile
import org.joda.time.DateTime
import support.HandsOnSuite
import org.scalatest._

import scala.collection.JavaConversions._
import scala.util.Random

// Doc is here -> http://twitter.github.io/scalding/#package

object Utils {

  case class Bid(uid: String, price: Double, id: String, timestamp: Long)
  case class Display(uid: String, id: String, timestamp: Long)
  case class Click(uid: String, id: String, ctr: Double, timestamp: Long)

  // this function will help us generate fake data
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

  // fake generated data
  lazy val (bidsList, displaysList, clicksList) = {
    val (bl,dlOpt,clOpt) = (1 to 100).map{ _ => gen}.unzip3
    (bl, dlOpt.flatten, clOpt.flatten)
  }



  // core flow functions

  // Doc is here -> http://twitter.github.io/scalding/#package

  def topNWords(input: TypedPipe[String], n: Int): TypedPipe[String] = {
    ???
  }

  def topNWordsWithAtLeastMLetters(input: TypedPipe[String], n: Int, m: Int): TypedPipe[String] = {
    ???
  }

  def innerJoin(bids: TypedPipe[Bid], displays: TypedPipe[Display], clicks: TypedPipe[Click]): TypedPipe[(Bid, Display, Click)] = {
    ???
  }

}

class ScaldingWordCount(args: com.twitter.scalding.Args) extends Job(args) {
  // I define some entry point (sources) and create a TypedPipe from them.
  val input = TypedPipe.from(TextLine(args("source")))

  // I prepare some sink as output of the job.
  // Each source must be connected to at least one sink and vice versa.
  val output1 = TypedTsv[String](args("output1"))
  val output2 = TypedTsv[String](args("output2"))

  // The flow transformation consists of function from TypedPipe to TypedPipe.
  // And finally wirering to a sink with the write method.
  Utils
    .topNWords(input, 10)
    .write(output1)
  Utils
    .topNWordsWithAtLeastMLetters(input, 10, 5)
    .write(output2)
}



class ScaldingJoin(args: com.twitter.scalding.Args) extends Job(args) {
  import Utils._
  // I define some entry point (sources) and create a TypedPipe from them.
  val bids: TypedPipe[Bid] = TypedPipe.from(TypedSequenceFile[Bid](args("bids")))
  val displays: TypedPipe[Display] = TypedPipe.from(TypedSequenceFile[Display](args("displays")))
  val clicks: TypedPipe[Click] = TypedPipe.from(TypedSequenceFile[Click](args("clicks")))

  // I prepare some sink as output of the job.
  // Each source must be connected to at least one sink and vice versa.
  val output = TypedSequenceFile[(Bid, Display, Click)](args("output"))

  // The flow transformation consists of function from TypedPipe to TypedPipe.
  // And finally wirering to a sink with the wriflattente method.
  Utils.innerJoin(bids, displays, clicks).write(output)
}




class Intro extends FunSuite with BeforeAndAfter with ShouldMatchers{
  import Utils.{Bid, Display, Click}

  val lorem: Seq[String] = scala.io.Source.fromFile("lorem.txt").getLines().toSeq


  test("toto") {
    //  exercice("you can test a job easily by mocking source and validating output") {
    JobTest.apply[ScaldingWordCount]
      .arg("source", "/user/source")
      .arg("output1", "/user/output1")
      .arg("output2", "/user/output2")
      .source(TextLine("/user/source"), lorem)(new TupleSetter[String] {
        override def apply(arg: String): Tuple = {
          val t = new Tuple
          t.add(0)
          t.add(arg)
          t
        }
        override def arity: Int = 2
      })
      .sink[String](TypedTsv[String]("/user/output1")) { output: scala.collection.mutable.Buffer[String] =>
        output.toList shouldEqual List("an", "id", "ex", "cu", "ad", "te", "ut", "et", "ea", "no")
      }
      .sink(TypedTsv[String]("/user/output2")) { output: scala.collection.mutable.Buffer[String] =>
        output.toList shouldEqual List("cotidieque", "oportere", "consectetuer", "adversarium", "verear",
          "posidonium", "philosophia", "fierent", "eloquentiam", "democritum")
      }(new TupleConverter[String] {
        override def apply(te: TupleEntry): String = te.getTuple.getString(0)

        override def arity: Int = 1
      })
      .run
      .finish
    //  }
  }

  test("titi") {
    //  exercice("implement a join") {
    JobTest.apply[ScaldingJoin]
      .arg("bids", "/user/bids")
      .arg("displays", "/user/displays")
      .arg("clicks", "/user/clicks")
      .arg("output", "/user/output")
      .source[Bid](TypedSequenceFile[Bid]("/user/bids"), Utils.bidsList)(TupleSetter.singleSetter[Bid])
      .source[Display](TypedSequenceFile[Display]("/user/displays"), Utils.displaysList.flatten)(TupleSetter.singleSetter[Display])
      .source[Click](TypedSequenceFile[Click]("/user/clicks"), Utils.clicksList)(TupleSetter.singleSetter[Click])
      .sink[(Bid, Display, Click)](TypedSequenceFile[(Bid, Display, Click)]("/user/output")) { output: scala.collection.mutable.Buffer[(Bid, Display, Click)] =>
        output.toList should have size Utils.clicksList.size
      }(TupleConverter.singleConverter[(Bid, Display, Click)])
      .run
      .finish
    //  }
  }
}
