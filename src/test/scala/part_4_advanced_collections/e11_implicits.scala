package part_4_advanced_collections

import support.HandsOnSuite

class e11_implicits extends HandsOnSuite {
  /*
  Implicit in Scala is a way to do dependency injection based on types.
  It occurs in the last method and function parameter block.

  When you call a function with implicit parameter, either you pass it explicitely,
  or the compiler will take one object from the current implicit scope with a matching
  type signature.

  Implicit values reside in a scope called implicit scope.
  It holds all instances with their types with modifier `implicit`.
  */

  def plus(i: Int)(implicit j: Int): Int = i + j

  exercice("I can pass implicit parameter explicitely") {
    plus(2)(3) should be (__)
  }

  exercice("I can ommit implicit parameter if I already have one matching implicit value in scope") {
    implicit val toto = 3
    plus(2) should be (__)
  }

  sealed trait Mode
  case object Local extends Mode
  case object Distributed extends Mode

  def doStuff(name: String)(implicit mode: Mode) = {
    mode match {
      case Local => s"local ${name}"
      case Distributed => s"remote ${name}"
    }
  }

  exercice("Implicits can be passed from function to function") {
    def doMoreStuff(name: String, age: Int)(implicit mode: Mode) = {
      doStuff(name) + " " + age.toString
    }

    implicit val mode = Local
    doStuff("mathieu") should be (__)
    doMoreStuff("mathieu", 28) should be (__)
  }

  exercice("Implicit scope are local") {
    // this does not compile

    // def doMoreStuff(name: String, age: Int) = {
    //   doStuff(name) + " " + age.toString
    // }

    implicit val mode = Local
    doStuff("mathieu") should be (__)

    // doMoreStuff("mathieu") should be (__)
  }

  exercice("I can import implicit value in local scope") {
    object Dummy {
      implicit val mode = Distributed
    }

    import Dummy.mode

    doStuff("mathieu") should be (__)
  }
}
