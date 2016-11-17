package part_3_functional_programming

import support.HandsOnSuite

/**
* Let's move on extractors and pattern matching
*/

class e9_extractors_and_pattern_matching extends HandsOnSuite {
 /**
  * An extractor is the opposite of a constructor.
  * We define an extractor by adding an `unapply` method inside
  * the companion object of a type
  *
  * A companion object is as a singleton with the same name as the class
  * and can be considered as a toolbox holding the static code of a class
  */
  exercice("An extractor is the opposite of a constructor") {
    class Email(val value:String)
    object Email { def unapply(email:Email): Option[String] = Option(email.value) }

    val mailstring = "foo@bar.com"
    val email = new Email(mailstring)

    val Email(extractedString) = email
    // What the compiler actually does:
    // val extractedString = Email.unapply(email).get

    (extractedString == mailstring) should be(__)
  }

 /**
  * Extractors can have multiple return values
  */
  exercice("Extractors can have multiple return values") {
    class Email(val value:String, val spamRatio:Integer)
    object Email {
      def unapply(email:Email): Option[(String,Integer)] = Option((email.value,email.spamRatio))
    }

    val email = new Email("foo@bar.com",5)
    val Email(extractedString,extractedRatio) = email

    (extractedRatio) should be(__)
    (extractedString) should be(__)
  }

 /**
  * Creating a case class automatically defines one extractor
  * for this case class
  */
  exercice("An extractor is defined for each case classes") {
    case class Email(val value:String)

    val mailstring = "foo@bar.com"
    val email = new Email(mailstring)
    val Email(extractedString) = email

    (extractedString) should be(__)
  }

 /*
 *   Pattern matching is a generalisation of switch case blocks, found in other programming languages such as C or Java,
 *     to class hierarchy
 *   Only the keyword `match` is needed to pattern match then `case` to differentiate all patterns
 *
 *       e match {case p1 => e1 ... case pn => en },
 *
 *   where pi represent patterns and ei return values in case pattern pi match input e
 *
 *   `match` is an expression, thus it always returns a value.
 *   In case there is no pattern matching the input a MatchError is raised
 *
 */
 /**
  * You can pattern match on strings
  */
  exercice("pattern match is similar to switch cases") {
    val string="B"

    val actual = "B" match {
      case "A" => "stringA"
      case "B" => "stringB"
      case "C" => "stringC"
      // The notation `_` define something that do not need naming
      // The case _ handles every other cases and avoids MatchErrors
      case _ => "DEFAULT"
    }

    actual should be (__)

    val nextActual = "E" match {
      case "A" => "stringA"
      case "B" => "stringB"
      case "C" => "stringC"
      case _ => "DEFAULT"
    }

    nextActual should be (__)
  }

  exercice("Order matters inside a pattern matching") {
    val actual = "A" match {
      case _ => "DEFAULT"
      case "A" => "found A"
    }

    actual should be (__)
  }



  exercice("You can use pattern matching with case classes to capture inner values") {
    case class A(/* compiler add `val` here */ a:String
               , /* compiler add `val` here */ b:String)

    val a:A = /* We can do A(.., ...) instead of new A(..., ...)
       because A's companion object computed by the compiler define A$.apply(..., ...) */
              A(a="string", b="B")

    val actual = a match {
      case A(a,b) => a+b
      case _ => "DEFAULT"
    }

    actual should be (__)
  }

  exercice("You do not have to capture all values") {
    case class A(val a:String, val b:String)
    val a:A = new A(a="string", b="B")

    val actual = a match {
      case A(a,_) => a
      case _ => "DEFAULT"
    }

    actual should be (__)
  }



  exercice("Lists have several pattern matching") {
    val s = Seq("a","b","c")
    val actual = s match {
      case Seq("a","b","c") => "ok"
      case _ => "DEFAULT"
    }

    actual should be (__)

    val consActual = s match {
      case "a"::Nil => "ko"
      case "a"::"b"::"c"::Nil => "ok"
      case _ => "DEFAULT"
    }

    consActual should be (__)

    val headtailActual = s match {
      case head::tail => tail
      case _ => "DEFAULT"
    }

    headtailActual should be (__)
  }

}
