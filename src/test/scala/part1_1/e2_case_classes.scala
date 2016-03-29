package part1_1

import support.HandsOnSuite

/**
*  Case classes
*
*  You define a case class by adding the `case`keyword.
*
*  Case classes are regular classes which export their constructor parameters
*  and which provide a recursive decomposition mechanism via pattern matching.
*
*  To construct an instance of case class, there is no need to use the `new` keyword.
*
*  All case classes automatically define equals, toString, hashcode and a copy method.
*
*  Case classes are useful to define algebraic datatypes.
*/
class e2_case_classes extends HandsOnSuite {
  /**
  * Instantiating a case class
  */
  exercice("It's easy to instantiate a case class") {
    case class MyDog(name: String, breed: String) // by default parameters are values

    val d1 = MyDog("Scooby", "Doberman")
    val d2 = MyDog("Rex", "Custom")
    val d3 = new MyDog("Scooby", "Doberman") // You can also instantiate using `new` but you don't have to.
    val d4 = MyDog.apply("Rex", "Custom") // Actually you can instantiate using the companion object apply method

    (d1 == d3) should be(__)
    (d1 == d2) should be(__)
    (d2 == d3) should be(__)
    (d2 == d4) should be(__)
  }

  /**
  * Equality
  */
  exercice("Case classes define Equality") {
    case class Person(firstname: String, lastname: String)

    val p1 = new Person("Martin", "Odersky")
    val p2 = new Person("Emmanuel" , "Bernard")
    val p3 = new Person("Martin", "Odersky")

    // actually, == calls the .equals method
    (p1 == p2) should be(__)
    (p1 == p3) should be(__)

    // The eq method checks reference equality (like '==' in Java)
    (p1 eq p2) should be(__)
    (p1 eq p3) should be(__)
  }

  /**
  * Hashcode.
  */
  exercice("Case classes define an hash function") {
    case class Person(prenom: String, nom: String)

    val p1 = new Person("Iron", "Man")
    val p2 = new Person("Super", "Man")
    val p3 = new Person("Iron", "Man")

    (p1.hashCode == p2.hashCode) should be(__)
    (p1.hashCode == p3.hashCode) should be(__)
  }


  /**
  * Accessors
  */
  exercice("Case class define Accessors") {
    case class MyDog(name: String, breed: String)

    val d1 = MyDog("Scooby", "Doberman")
    d1.name should be(__)
    d1.breed should be(__)

    // What will happen ?
    //d1.name = "Scooby Doo"
  }

  /**
   * Case classes are usually immutable, so you have to copy them
   */
  exercice("Update a value by copying it") {
    case class MyDog(name: String, breed: String)

    val d1 = MyDog("Scooby", "Doberman")

    val d2 = d1.copy(name = "Scooby Doo") // copy the value with a new name

    d1.name should be(__) // the original instance has not been touched
    d1.breed should be(__)

    d2.name should be(__)
    d2.breed should be(__) // other properties are automatically copied from the original instance
  }

  /**
  * Case class constructors, like any other methods, can define named parameters and default values
  */
  exercice("default and named parameters") {
    case class Person(firstname: String, lastname: String, age: Int = 0, phone: String = "")

    val p1 = Person("Sherlock", "Holmes", 23, "06-XX-XX-XX-XX")

    // omitting some parameters
    val p2 = Person("Doctor", "Watson")

    // parameters order is not important if you use named parameters
    val p3 = Person(lastname = "Professor", firstname = "Moriarty", phone = "01-XX-XX-XX-XX")

    // copy using named parameters
    val p4 = p3.copy(age = 23)

    p1.firstname should be("Sherlock")
    p1.lastname should be("Holmes")
    p1.age should be(23)
    p1.phone should be(__)

    p2.firstname should be("Doctor")
    p2.lastname should be(__)
    p2.age should be(0)
    p2.phone should be(__)

    p3.firstname should be(__)
    p3.lastname should be("Professor")
    p3.age should be(0)
    p3.phone should be(__)

    (p3 == p4) should be(__)
  }
}
