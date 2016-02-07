package support

import org.scalatest._
import org.scalatest.matchers.Matcher

import recorder._

import language.experimental.macros


trait HandsOnSuite extends MyFunSuite with Matchers {
  def __ : Matcher[Any] = {
    throw new NotImplementedError("__")
  }

  implicit val suite:MyFunSuite = this



  def anchor[A](a:A):Unit = macro RecorderMacro.anchor[A]

  def exercice(testName:String)(testFun: Unit)(implicit suite: MyFunSuite, anchorRecorder: AnchorRecorder):Unit = macro RecorderMacro.apply



  /*override protected def test(testName: String, tags: Tag*)(testFun: => Unit):Unit


  = macro RecorderMacro.apply  */


  protected override def runTest(testName: String, args: Args) = { // reporter: Reporter, stopper: Stopper, configMap: Map[String, Any], tracker: Tracker) {
    if (!CustomStopper.oneTestFailed) {
      super.runTest(testName, args.copy(reporter = new ReportToTheStopper(args.reporter), stopper = CustomStopper)) // , CustomStopper, configMap, tracker)
    } else {
      SucceededStatus
    }
  }
}

object HandsOnSuite {
  object partie1 extends Tag("support.partie1")
  object partie2 extends Tag("support.partie2")
}
