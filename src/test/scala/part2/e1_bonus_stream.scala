package part2

import support.HandsOnSuite

class e1_bonus_stream extends HandsOnSuite {

  sealed trait Stream[+A] {

    def map[B](f:A => B):Stream[B]

    def flatMap[B](f:A => Stream[B]):Stream[B]

    def filter(f:A => Boolean):Stream[A]

    final def union[B >: A](stream: => Stream[B]):Stream[B]= {
      this match {
        case cons:Cons[A] => Cons(cons.head, ???)
        case EmptyStream => ???
      }
    }

    def isEmpty:Boolean

    def take(n: Int): Stream[A] = {
      this match {
        case EmptyStream => EmptyStream
        case cons: Cons[A] => {
          if (n <= 0) EmptyStream
          else if (n == 1) Cons(cons.head, EmptyStream)
          else Cons(cons.head, cons.tail.take((n - 1)))
        }
      }
    }

    def foreach(sideEffect:A => Unit):Unit
  }

  object Stream {
    // 'A*' means var args of type A
    def apply[A](values:A*):Stream[A] = {
      if (values.isEmpty) {
        EmptyStream
      } else {
        // ':_*' allows to expand a list into var args
        Cons(values.head, Stream(values.tail:_*))
      }
    }

    def unapply[A](xs: Stream[A]): Option[(A, Stream[A])] = {
      xs match {
        case EmptyStream => None
        case cons:Cons[A] => Some((cons.head, cons.tail))
      }
    }
  }
  /**
    * Cons means Constructor, it allows to build a list by adding an element to the head
    */
  final class Cons[A](val head:A, tl: => Stream[A]) extends  Stream[A] {


    def isEmpty = false

    // This trick guarantees lazyness of the queue in the stream
    // as well as the memoization of accessed values
    // In other words, we only compute what we really access
    // And we store what we already computed
    lazy val tail:Stream[A] = tl

    def map[B](fonction:A => B):Stream[B] = new Cons(fonction(head), tail.map(fonction))


    /**
      * flatMap implementation needs function union
      */
    def flatMap[B](f:A => Stream[B]):Stream[B] = ???

    override def filter(f:A => Boolean):Stream[A] = ???

    override def equals(that:Any):Boolean = ???

    override def hashCode():Int = head.hashCode()

    override def foreach(sideEffect:A => Unit):Unit = {
      sideEffect(head)
      tail.foreach(sideEffect)
    }
  }

  object Cons {
    def apply[A](head:A, tl: => Stream[A]) = new Cons(head,tl)

    def unapply[A](cons:Cons[A]) = Stream.unapply(cons)
  }

  /**
    * There is only one EmptyStream, thus it can be a case object (case class singleton)
    */
  case object EmptyStream extends Stream[Nothing] {
    type A = Nothing

    def map[B](f:A => B):Stream[B]  = ???

    def flatMap[B](f:A => Stream[B]):Stream[B] = ???

    def filter(f:A => Boolean):Stream[A] = ???

    def isEmpty: Boolean = true

    def foreach(effetDeBord:A => Unit):Unit = {}
  }

  exercice("creation") {

    Stream() should be(EmptyStream)

    Stream(1,2,3) should be(Cons(1,Cons(2,Cons(3,EmptyStream))))

  }

  exercice("map") {
    Stream(1,2,3).map(x => x + 1) should be(Stream(2,3,4))
  }


  exercice("union") {

    Stream(1,2,3).union(Stream(4,5)) should be(Stream(1,2,3,4,5))

    Stream(1,2,3).union(Stream("A","B","C")) should be(Stream(1,2,3,"A","B","C"))
    // This example is a bit complex, the compiler is looking for a b validating
    // String <: B and A (here Int) <: B
    // The first common type between String and Int is Any, corresponding to Object in Java

    // There is nothing to do for this example to actually work, the compiler does the job.

  }

  exercice("flatMap") {

    val combination = for (a <- Stream("A","B"); i <- Stream(1,2)) yield (a + i)

    combination should be( Stream("A1","A2","B1","B2"))

  }

  exercice("filter") {

    Stream(1,2,3).filter(x => x > 1) should be(Stream(2,3))

    Stream(1,2,3).filter(x => false) should be(EmptyStream)
  }

  exercice("lazyness") {

    val s = Stream(1,2,3).map{
      case 1 => 1
      case _ => {
        throw new Exception("I should be lazy")
      }
    }

    assert(!s.take(1).isEmpty)


  }

  exercice("lazyness 2") {
    val s:Stream[Int] = Stream(1,2,3).map{
      case 1 => 1
      case _ => {
        throw new Exception("I should be lazy")
      }
    }

    val s2 = for (i <- s; j <- s) yield(i + j)

    s2.take(1).foreach(println)
  }


}
