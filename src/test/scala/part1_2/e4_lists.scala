package part1_2


import support.HandsOnSuite


  /**
  *   Let's move on to lists...
  *
  *   Starting point of scala collections is trait Iterable.
  *   Higher order function (map, filter, foreach...) that we will see next
  *   can be used on classes of Collection API.
  *   Collections are immutable by default, however it is possible to use their mutable form.
  *
  *   Actually Scala promote to use immutability (Scala has been designed in care of thread safety)
  *
  *   A List containing elements x1,…,xn is printed List(x1, … , xn).
  *
  *   List class consists of a composition of two case classes: `Nil` (empty list) and `::` (cons)
  *   x::xs represent a list with the element x followed by the list xs
  *   List is a recursive structure and element types must be the same in the whole list.
  *
  *   Lists have usefull functions: isEmpty, filter, head, tail...
  *
  *   This can help: http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List
  */

class e4_lists extends HandsOnSuite {

  /**
  *   Nil is and will always be the empty list whatever the type of the list !
  */
  exercice("Nil lists are always equals") {
    val a: List[String] = Nil
    val b: List[Int] = Nil

    (a == Nil) should be(__)
    (b == Nil) should be(__)
    (a == b) should be(__)

  }

  /**
  *   Watchout, we saw this in exo 2 on case classes !
  */
  exercice("Eq tests reference equality on objects") {
    val a = List(1, 2, 3)
    val b = List(1, 2, 3)

    (a eq b) should be(__)
  }

  /**
  *  Some usefull methods...
  */
  exercice("head and tail functions") {
    val a = List(1, 3, 5, 7, 9)

    // accesses the element at index 2 in the list
    a(2) should equal(__)
    a.head should equal(__)
    a.tail should equal(__)
    a.length should equal(__)
    a.reverse should equal(__)
    // convert a List into a String
    anchor(a.toString)

    // multiplies by 3 each element in the list
    // `map` function compute an operation on each element of a collection
    // it return a copy of the calling collection by applying the function given in parameter to each elem
    a.map {v => v * 3} should equal(__)

    // keeps all multiples of 3
    a.filter {v => v % 3 == 0} should equal(__)

    // keeps all multiples of 5
    val c = List(1, 2, 5, 8, 9)
    val b = c.filterNot(v => v % 5==0)
    c should equal(List(1, 2, 5, 8, 9)) // les listes sont immuables par défaut !
    b should equal(__)
  }

  /**
  *   The use of `_` define a parameter that does not require naming
  */
  exercice("Functions applied to lists can use `_`") {
    val a = List(1, 2, 3)
    //ici _ * 2 veut dire i => i * 2
    a.map(_ * 2) should equal(__)
    a.filter(_ % 2 != 0) should equal(__)
  }
}
