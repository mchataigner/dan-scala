package part4

import support.HandsOnSuite


/*
* Here we define our class hierarchy of json types
* with string, number, seq and object.
* representing all possible json types
* making all derive the same trait JsValue.
* This trait is sealed, it can not be extended outside
* the current compilation unit. Try to create a class
* inheriting this trait in another file.
*/
sealed trait JsValue

case class JsString(s:String) extends JsValue{
  override def toString():String= ???
}
case class JsNumber(n:Number) extends JsValue{
  override def toString():String= ???
}
case class JsSeq(seq:Seq[JsValue]) extends JsValue{
  override def toString():String= ???
}
case class JsObject(properties:Map[String, JsValue]) extends JsValue{
  override def toString():String= ???
}

/*
* We create a companion object a JsObject from a list of
* key value pairs of type String -> JsValue
*/
object JsObject{
  def apply(properties:(String,JsValue)*):JsObject= ???
}

/*
* Now, let try some magic with type class
* It's a generic trait that take a value of type A and return
* a JsValue representing it.
*/
trait Writer[A]{
  def write(value:A):JsValue
}

/*
* Now the companion object allowing to create writers without specifying
* the generic type, because the compiler can infer it from the type of the function
* given in parameter to apply
*/
object Writer{
  def apply[A]( f: A=>JsValue ):Writer[A]={
    new Writer[A]{
      override def write(value:A)=f(value)
    }
  }
}

/*
* Within this object we define writers that matters to us.
* The point of marking them as implicit is that if we import them at one place in code
* they will be present for the whole scope of this code.
*/
object Implicits {
  implicit val stringWriter=Writer { s:String=> ??? }
  implicit val intWriter=Writer { n:Int=> ??? }
  implicit val doubleWriter=Writer { n:Double=> ??? }
  implicit val bigDecimalWriter=Writer { n:BigDecimal=> ??? }
  implicit def seqWriter[B](implicit writer:Writer[B])= Writer { seq:Seq[B] => ??? }
}

/*
* Eventually, the serializer that takes an object of type A and implicitelly
* a writer of type Writer[A]. There is no need to import actual writers here,
* we will only need to have them in the implicit scope of calling code.
*/
object Json{
  def toJson[A](value:A)(implicit writer:Writer[A]):JsValue={
    writer.write(value)
  }
}



import Implicits._

class e0_json_writer extends HandsOnSuite {
  exercice("a Json tree should print itself"){
    val jsonString=JsString("json")
    val jsonNumber=JsNumber(int2Integer(10))
    val jsonSequece=JsSeq(Seq(JsString("a"), JsString("b"), JsString("c")))
    val jsonObject=JsObject(Map("string"->jsonString, "number"->jsonNumber, "seq"->jsonSequece))

    val expected:String="""{
      |  "string":"json",
      |  "number":10,
      |  "seq":["a","b","c"]
      |}""".stripMargin

    val actual=jsonObject.toString()

    actual should equal(expected)
  }
  exercice("toJson should correctly convert a String"){
    val expected=JsString("json")
    val actual=Json.toJson("json")

    actual should equal(expected)
  }
  exercice("toJson should correctly convert an Integer"){
    val expected=JsNumber(12)
    val actual=Json.toJson(12)

    actual should equal(expected)
  }
  exercice("toJson should correctly convert a Double"){
    val expected=JsNumber(12.0)
    val actual=Json.toJson(12.0)

    actual should equal(expected)
  }
  exercice("toJson should correctly convert a BigDecimal"){
    val expected=JsNumber(BigDecimal("99999999999999999999999999999999999999999999999999999999999999999999999999"))
    val actual=Json.toJson(BigDecimal("99999999999999999999999999999999999999999999999999999999999999999999999999"))

    actual should equal(expected)
  }
  exercice("toJson should correctly convert a Sequence of strings"){
    val expected=JsSeq(Seq(JsString("a"),JsString("b"),JsString("c")))
    val actual=Json.toJson(Seq("a","b","c"))

    actual should equal(expected)
  }
}
package client {
  /*
  * We simulate here a user of our JSON library,
  * he wants to serialize an object defined in his code.
  * Of course, this object is not known of the library.
  * However we will be able to extend it in order to keep
  * type safety and compile time guarantees.
  */
  case class User(name:String,age:Int,friends:Seq[String])

  object User{
    implicit val userWrite:Writer[User] = Writer { u:User => ??? }
  }

  import User.userWrite
  class e0_json_writer_client extends HandsOnSuite {
    exercice("toJson should correctly convert a user"){
      val user = User("Mathieu", 25, Seq("Jean","Jon","Ludwine"))

      val expected=JsObject(Map("name"->JsString("Mathieu"), "age"->JsNumber(25), "friends"->JsSeq(Seq(JsString("Jean"),JsString("Jon"),JsString("Ludwine")))))
      val actual=Json.toJson(user)

      actual should equal(expected)
    }
  }
}
