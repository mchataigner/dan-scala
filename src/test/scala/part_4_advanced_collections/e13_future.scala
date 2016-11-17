package part_4_advanced_collections

import support.HandsOnSuite

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util._

/**
 * Futures provide a way to reason about asynchronous and parallel
 * code execution. A Future handles a value that may not yet exist.
 * And you can manipulate this value almost as another value by
 * applying function on it. All the computation are lazy and will
 * be executed when the value is actually available.
 */
class e13_future extends HandsOnSuite {

  // Future is part of the Scala standard API.
  import scala.concurrent._

  // We need an execution context to run our asynchronous code.
  import scala.concurrent.ExecutionContext.Implicits.global

  // Here I define a helper function to declare a
  // Future value that will become available after a
  // fixed timeout. You will use it in your tests, but
  // don't look yet at how it is implemented.
  val timer = new java.util.Timer
  def eventually[A](v: => A): Future[A] = {
    val a = Promise[A]()
    timer.schedule(new java.util.TimerTask {
      def run = a.success(v)
    }, scala.util.Random.nextInt(1000) + 1000)
    a.future
  }

  exercice("manipulating a future value") {
    val eventuallyInt: Future[Int] = eventually(88)

    // Let's multiply this int by 2.
    // How can we do this without blocking the thread?
    val eventuallyResult: Future[Int] = ???

    // Here we block the thread until the future is completed
    Await.result(eventuallyResult, atMost = 5 seconds) should be(176)
  }

  exercice("composing a future with another future value") {
    val eventuallyInt: Future[Int] = eventually(88)
    val eventuallyMultiplier: Future[Int] = eventually(2)

    // Let's multiply this eventuallyInt by eventuallyMultiplier
    // How can we do this without blocking the thread?
    val eventuallyResult: Future[Int] = ???

    Await.result(eventuallyResult, atMost = 5 seconds) should be(176)
  }

  exercice("you can create already completed Future") {
    val eventuallyInt: Future[Int] = Future.successful(88)

    // It is actually just a future...
    Await.result(eventuallyInt, atMost = 5 seconds) should be(__)
  }

  exercice("if the asynchronous computation fails, the Future handles the failure") {
    // As one can create an already successful future, one can create
    // an already failed future
    val failedInt: Future[Int] = Future.failed(new Exception("Something wrong happened"))

    // What if we block and try to get the actual Future value?
    an [Exception] should be thrownBy ???
  }

  exercice("Let's recover a failed Future") {
    // Future failures do not propagate on the stack like exceptions
    // so you can't really use try/catch
    val eventuallyInt: Future[Int] = Future.failed(new Exception("Something wrong happened"))

    val eventuallyResult = eventuallyInt.recover {
      case e => -1
    }

    Await.result(eventuallyResult, atMost = 5 seconds) should be(__)
  }

  exercice("you can also apply side effects at future completion") {
    val eventuallyInt: Future[Int] = eventually(88)

    var x = 0

    // Apply a side effect once the future is completed
    eventuallyInt.andThen {
      case Success(value) => x = value * 2
      case Failure(e) => throw e
    }

    // Take care with side effects and asynchronous computations!
    x should be(__)

    // Hint: in theory there could be a race here, but it is very unlikely to happen
    // because of the initial conditions we choose for this example...
  }

  exercice("let's sum a sequence of future values") {
    val eventuallyInts: Seq[Future[Int]] = (1 to 32).map(i => eventually(i))

    // Let's sum all these values.
    // How can we do this without blocking the thread?
    val eventuallySum: Future[Int] = ???

    Await.result(eventuallySum, atMost = 5 seconds) should be(528)
  }

  exercice("let's do computations in parallel") {
    // This run the computation in another thread
    def asyncComputation(a: Int) = Future { a * 2 }

    val someInts = List(1, 2, 3, 4)

    // Let's apply our async computation for all these ints
    // How can we do this without blocking the thread?
    val eventuallyReult: Future[Seq[Int]] = ???

    Await.result(eventuallyReult, atMost = 5 seconds) should be(List(2, 4, 6, 8))
  }

  exercice("a bit more complicated") {
    // This run the computation in another thread
    def asyncComputation(a: Int) = Future { 10 / a }

    val someInts = List(0, 1, 2, 3)

    // Let's apply our async computation for all these ints
    // How can we do this without blocking the thread?
    val eventuallyReult: Future[Seq[Int]] = ???

    Await.result(eventuallyReult, atMost = 5 seconds) should be(List(10, 5, 3))
  }

  exercice("generating a Future") {
    val p = Promise[Int]()

    // How to redeem this promise with a value?
    ???

    Await.result(p.future, atMost = 5 seconds) should be(42)
  }

  exercice("you can manipulate the Future monad with a for comprehension") {
    def asyncComputation(a: Int) = Future { a * 10 }

    val result: Future[Int] = for {
      a <- eventually(12)
      b = a * 2
      c <- asyncComputation(b)
    } yield ???

    Await.result(result, atMost = 5 seconds) should be(240)

    // Try to rewrite the same without for comprehension
    val result2: Future[Int] = ???

    Await.result(result2, atMost = 5 seconds) should be(240)
  }

  exercice("write your own future like data structure") {

    // Do not try this at home...this way of creating a Future
    // is very naive and not thread safe (+ it has an unsafe API)
    case class MyFuture[A]() {
      var value = Option.empty[A]
      val actions = collection.mutable.ListBuffer.empty[A => Unit]

      def map[B](f: A => B): MyFuture[B] = ???
      def flatMap[B](f: A => MyFuture[B]): MyFuture[B] = ???
      def redeem(value: A): Unit = ???
      def get: A = ???
    }

    val eventuallyA: MyFuture[Int] = MyFuture[Int]()
    val eventuallyB = eventuallyA.map(a => a * 2)
    val eventuallyC = eventuallyB.flatMap(b => eventuallyA.map(a => a * b))

    eventuallyA.redeem(42)

    eventuallyC.get should be(3528)
  }

}
