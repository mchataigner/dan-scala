package part4

import support.HandsOnSuite

/*
* Now, let try some magic with type class
* It's a generic trait that take a value of type A and return
* a JsValue representing it.
*/
trait Writer2[A]{
  def write(value:A):JsValue
}

/*
* Now the companion object allowing to create writers without specifying
* the generic type, because the compiler can infer it from the type of the function
* given in parameter to apply
*/
object Writer2{
  def apply[A]( f: A => JsValue):Writer2[A]={
    new Writer2[A]{
      override def write(value:A)=f(value)
    }
  }
  implicit val stringWriter2=Writer2{ s:String => ??? }
  implicit val intWriter2=Writer2{ n:Int => ??? }
  implicit val doubleWriter2=Writer2{ n:Double => ??? }
  implicit val bigDecimalWriter2=Writer2{ n:BigDecimal => ??? }
  implicit def seqWriter2[B](implicit writer:Writer2[B])= Writer2 { seq:Seq[B] => ??? }
}

object JSONWriter2{
  def toJson[A](value: A)(implicit writer: Writer2[A]): JsValue = writer.write(value)
}


class e1_json_reader extends HandsOnSuite {
  // The implicit scope correspond to :
  // - the caller's implicit
  // - companion object of the implicit type
  exercice("implicit scope magic"){
    val value = "toto"
    val expected = JsString("toto")
    // Here, you don't need to import Writer2's implicits
    // definitions because the type of the implicit parameter is
    // Writer2, it will look up in Writer2 companion object as well
    val actual = JSONWriter2.toJson(value)
    actual should equal(expected)
  }
}
