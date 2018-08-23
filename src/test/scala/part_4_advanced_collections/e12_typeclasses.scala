package part_4_advanced_collections

import support.HandsOnSuite

/**
* Type classes are kind of ad-hoc polymorphysm.
* It can extends behavior of some class without inheritance.
* It enables implementing new behavior without loose coupling.
*
* Implicit scopes are a bit more than just local.
*
* There are multiple levels of implicit:
*  - local scope: local implicit definition or implicit parameters function definition
*  - companion object of implicit type or companion object of generic parameter of implicit type
*
* If the compiler could not find value in implicit scope matching expected type, it will throw
* an error and abort the compilation.
*
* If the compiler finds more than one value in implicit scope matching expected type, it will throw
* an error and abort the compilation except if one implicit is more specific than the other:
* List[Int] is more specific than List[T], so if we are looking for an implicit List[Int] and we have List[Int] and List[T]
* in scope, it will take the List[Int].
*
* For example, I have a class Monoid[T] which has a generic type T,
* if I am looking for an implicit value of type Monoid[T], it will look
* in local scope *and* companion object of Monoid if present *and*
* companion object of type T if present.
*/

class e12_typeclasses extends HandsOnSuite {

  /**
    * Monoid is an abstraction that takes 2 elements of same type T and combine them
    * to a new element of type T
    * @tparam T
    */
  trait Monoid[T] {
    def plus(a: T, b: T): T
  }

  object Monoid {
    // helper method to create Monoid instance from a function
    def from[T](f:(T,T) => T) = new Monoid[T] {
      override def plus(a: T, b: T): T = f(a,b)
    }

    // this is an abstraction of sum of Int
    implicit val intMonoid: Monoid[Int] = ???

    // this is an abstraction of list concatenation
    implicit def listMonoid[T](): Monoid[List[T]] = ???

    // generic sum of tuples
    implicit def genericTupleMonoid[T: Monoid](): Monoid[(T,T)] = {
      val imon = implicitly[Monoid[T]]
      new Monoid[(T, T)] {
        override def plus(a: (T, T), b: (T, T)): (T, T) = {
          anchor("generic tuple monoid")
          ???
        }
      }
    }

    // specific sum of tuples of ints
    implicit def intTupleMonoid: Monoid[(Int,Int)] = new Monoid[(Int,Int)]{
      override def plus(a: (Int, Int), b: (Int, Int)): (Int, Int) = {
        anchor("specific tuple of int monoid")
        ???
      }
    }
  }

  case class Bidule(value: Int, items: List[String])

  object Bidule {
    implicit val biduleMonoid: Monoid[Bidule] = ???
  }


  /**
    * Abstract definition of sum that `sum` a list of elements of type T
    * It is not restricted to numbers. You can use `sum` provided you have
    * a monoid, or a way to create a monoid for your type
    *
    * @param elems list of elements to sum
    * @tparam T generic type
    * @return
    */
  // def sum[T](elems: List[T])(implicit monoid: Monoid[T]) = elems.reduce((v1, v2) => monoid.plus(v1,v2) )
  // def sum[T](elems: List[T])(implicit tMonoid: Monoid[T]) = elems.reduce( tMonoid.plus(_, _) )
  // def sum[T](elems: List[T])(implicit tMonoid: Monoid[T]) = elems.reduce( tMonoid.plus )
  def sum[T:Monoid](elems: List[T]) = elems.reduce( implicitly[Monoid[T]].plus )


  exercice("I can give the monoid explicitely") {
    sum(List(2,3,4))(Monoid.intMonoid) should be (__)
  }


  exercice("It will find my monoid in local scope"){
    implicit val doubleMonoid = ???
    sum(List(0.06,1.1,2.2,3.3)) should be (__)
  }


  exercice("It can find my monoid in companion object of Monoid"){
    sum(List(2,3,4)) should be (__)
  }


  exercice("It can find my monoid in the companion object of the generic type") {
    sum(List(Bidule(2, List("a","b")), Bidule(1, List("c")))) should be (__)
  }

  exercice("I can import implicit definition of my monoid") {
    object Dummy {
      implicit val toto: Monoid[String] = ???
    }
    import Dummy._
    sum(List("a","b","c")) should be (__)
  }
}
