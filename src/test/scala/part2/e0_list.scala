package part2


import support.HandsOnSuite


class e0_list extends HandsOnSuite {

  /*
  * We are going to define a list using recursions, as a linked list
  * Thus a list is either the empty list or one element followed by another list
  */
  sealed trait List[+A] {

    def map[B](f:A => B):List[B]

    def flatMap[B](f:A => List[B]):List[B]

    def filter(f:A => Boolean):List[A]

    final def union[B >: A](list:List[B]):List[B]= {
      this match {
        case Cons(head,tail) => Cons(head, ???)
        case Nil => ???
      }
    }

    //def isEmpty:Boolean
  }

  object List {
    // 'A*' means var args of type A
    def apply[A](values:A*):List[A] = {
      if (values.isEmpty) {
        Nil
      } else {
        // ':_*' allows to expand a list into var args
        Cons(values.head, List(values.tail:_*))
      }
    }
  }
  /**
   * Cons means Constructor, it allows to build a list by adding an element to the head
   */
  case class Cons[A](head:A, tail:List[A]) extends  List[A] {

    def map[B](f:A => B):List[B] = ???


    /**
     * flatMap implementation needs function union
     */
    def flatMap[B](f:A => List[B]):List[B] = ???

    def filter(f:A => Boolean):List[A] = ???

  }

  /**
   * There is only one Nil, thus it can be a case object (case class singleton)
   */
  case object Nil extends List[Nothing] {
    type A = Nothing

    def map[B](f:A => B):List[B]  = ???

    def flatMap[B](f:A => List[B]):List[B] = Nil

    def filter(f:A => Boolean):List[A] = ???
  }

  exercice("creation") {

    List() should be(Nil)

    List(1,2,3) should be(Cons(1,Cons(2,Cons(3,Nil))))

  }

  exercice("map") {
    List(1,2,3).map(x => x + 1) should be(List(2,3,4))
  }


  exercice("union") {

    List(1,2,3).union(List(4,5)) should be(List(1,2,3,4,5))

    List(1,2,3).union(List("A","B","C")) should be(List(1,2,3,"A","B","C"))
    // This example is a bit complex, the compiler is looking for a b validating
    // String <: B and A (here Int) <: B
    // The first common type between String and Int is Any, corresponding to Object in Java

    // There is nothing to do for this example to actually work, the compiler does the job.

  }

  exercice("flatMap") {

    val combination = for (a <- List("A","B"); i <- List(1,2)) yield (a + i)

    combination should be( List("A1","A2","B1","B2"))

  }

  exercice("filter") {

    List(1,2,3).filter(x => x > 1) should be(List(2,3))

    List(1,2,3).filter(x => false) should be(Nil)
  }


}
