package part1_2

import support.HandsOnSuite

/**
*   Higher order functions
*
*   Higher order functions are functions that can take functions as parameters and/or return functions.
*
*   For example: filter, map, flatmap...
*
*   Some syntax on function first:
*     - function are defined with the keyword `def`
*     - it's possible to define return type by adding `: ReturnType`
*
*           def addition(a: Int, b: Int): Int = a + b
*
*     - return is not required since in Scala the last expression of a block is return by default !
*
*/
class e8_higher_order_functions extends HandsOnSuite {

  /**
  *   A value can reference an `anonymous` function
  *
  *   Note : we can write lambda with or without {}
  *
  *   val lambda = {
  *     x: Int => x + 1
  *   }
  */
  exercice("Anonymous function as parameter") {
    val lambda = (x: Int) => x + 1
    def result = List(1, 2, 3) map lambda
    // Scala compiler do type inference, thus we can omit return types
    result should be(__)
  }

  /**
  * or more simply
  */
  exercice("simpler") {
    def result = List(1, 2, 3) map ( x => x + 1 )
    result should be(__)

    def evenSimpler = List(1, 2, 3) map ( _ + 1 )
    evenSimpler should be(__)
  }



  /**
  *  Higher order functions can return functions
  */
  exercice("Functions returning other functions") {
    def addWithoutSyntaxSugar(x: Int) = {
      new Function1[Int, Int]() {
        def apply(y: Int): Int = x + y
      }
    }

    addWithoutSyntaxSugar(1)(2) should be(__)

    //simpler
    def add(x: Int) = (y: Int) => x + y
    add(2)(3) should be(__)

    def fiveAdder = add(5)
    fiveAdder(5) should be(__)
  }

  /**
  * Higher order functions can take functions as parameters
  */
  exercice("Function taking another function in parameter. This helps function composition") {
    def makeUpper(xs: List[String]) = xs map {
      _.toUpperCase
    }
    def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) = {
      xs map sideEffect
    }
    makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))

    makeWhatEverYouLike(List("ABC", "XYZ", "123"), {
      x => x.toLowerCase
    }) should be(__)

    //using it inline
    List("Scala", "Erlang", "Clojure") map {
      _.length
    } should be(__)
  }

  /**
  * currying
  */
  exercice("Currying is a technique transforming a function with multiple parameters into a function with one parameter") {
    def multiply(x: Int, y: Int) = x * y
    // watchout '_'
    // it indicates that the compiler should not apply the function but referencing it
    val multiplyCurried = (multiply _).curried
    multiply(4, 5) should be(__)
    multiplyCurried(4)(5) should be(__)
  }


  exercice("Currying can create specialized functions") {
    def customFilter(f: Int => Boolean)(xs: List[Int]) = {
      xs filter f
    }
    def onlyEven(x: Int) = x % 2 == 0
    val xs = List(12, 11, 5, 20, 3, 13, 2)
    customFilter(onlyEven)(xs) should be(__)

    // Watch out '_'
    // it indicates that the compiler should not apply the function but referencing it
    val onlyEvenFilter = customFilter(onlyEven) _
    onlyEvenFilter(xs) should be(__)
  }
}
