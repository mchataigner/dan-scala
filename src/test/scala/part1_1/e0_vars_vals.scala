package part1_1


import support.HandsOnSuite


/**
*  1mn chrono ?
*/

/**
* val & var are keywords used to declare variables and values.
*   - var : allow to declare a variable (you can reassign it later to a value of the same type)
*   - val : allow to declare a value (immutable, you can't reassign another value to it)
*
* Type is added as an optional "Tag". You can omit it and let the compiler infer it, or tag the
* variable/value with an explicit type.
*
* def is the keyword to define a function (or a method but we will discuss the difference later)
*/
class e0_vars_vals extends HandsOnSuite {

  exercice("You can reassign variables") {
    var a: Int = 5
    anchor(a)
    a should be(__)

    anchor(a)

    a = 7

    anchor(a)

    a should be(__)
  }

  exercice("Values are immutables (like using final in Java)") {
    val a: Int = 5

    a should be(__)

    /*
    *  Questions:
    */
    // What happen if you add these lines?
    // a = 7
    // a should be (7)
  }

  exercice("Let's define a function") {
    def add(a: Int, b: Int): Int = {
      return a + b
    }

    add(5, 7) should be(__)
  }

  exercice("Note that the return keyword is generally not used") {
    def add(a: Int, b: Int): Int = a + b

    add(5, 7) should be(__)
  }

  exercice("It's because scala evaluates the whole expression as return value") {
    val simpleExpression = 3 + 6
    val advancedExpression = {
      val x = 2
      val y = 3
      x * y
    }

    simpleExpression should be(__)
    advancedExpression should be(__)
  }
}
