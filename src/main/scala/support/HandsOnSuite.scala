package support

import org.scalatest._
import org.scalatest.events._
import org.scalatest.matchers.{ShouldMatchers, Matcher}

import recorder._

import language.experimental.macros




trait HandsOnSuite extends MyFunSuite with ShouldMatchers {
  def __ : Matcher[Any] = {
    throw new NotImplementedError("__")
  }

  implicit val suite:MyFunSuite = this



  def anchor[A](a:A):Unit = macro RecorderMacro.anchor[A]

  def exercice(testName:String)(testFun: Unit)(implicit suite: MyFunSuite, anchorRecorder: AnchorRecorder):Unit = macro RecorderMacro.apply



  /*override protected def test(testName: String, tags: Tag*)(testFun: => Unit):Unit


  = macro RecorderMacro.apply  */


  protected override def runTest(testName: String, reporter: Reporter, stopper: Stopper, configMap: Map[String, Any], tracker: Tracker) {
    if (!CustomStopper.oneTestFailed) {
      super.runTest(testName, new ReportToTheStopper(reporter), CustomStopper, configMap, tracker)
    }
  }
}

object HandsOnSuite {
  object partie1 extends Tag("support.partie1")
  object partie2 extends Tag("support.partie2")
}
