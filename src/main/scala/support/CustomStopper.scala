package support

import org.scalatest.Stopper

object CustomStopper extends Stopper{
  var oneTestFailed = false
  override def apply() = oneTestFailed

  def testFailed : Unit = {
    oneTestFailed = true
    ()
  }
}