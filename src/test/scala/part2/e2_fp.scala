package part2

import support.HandsOnSuite

/**
 * Here are pretty simple code exercices,
 * but try to solve them with these additional constraints:
 *
 * - do NOT use any mutable data structure (use only scala.collection._ ones)
 * - do NOT use any variables (do not use the `var` keyword in your code)
 * - do NOT use `null`
 *
 * API docs are here: http://www.scala-lang.org/api/current/
 */
class e2_fp extends HandsOnSuite {

  exercice("not quite lisp") {

    // You have a meeting in a large apartment building, but the directions you got
    // are a little confusing:
    //
    // You start on the ground floor (floor 0) and then follows the instructions one
    // character at a time.
    //
    // An opening parenthesis, `(`, means you should go up one floor, and a closing
    // parenthesis, `)`, means you should go down one floor.
    //
    // The apartment building is very tall, and the basement is very deep;
    // you will never find the top or bottom floors.

    def targetFloor(instructions: String): Int = ???

    targetFloor("(())") should be (0)
    targetFloor("()()") should be (0)
    targetFloor("(((") should be (3)
    targetFloor("(()(()(") should be (3)
    targetFloor("))(((((") should be (3)
    targetFloor("())") should be (-1)
    targetFloor("))(") should be (-1)
    targetFloor(")))") should be (-3)
    targetFloor(")())())") should be(-3)
  }

  exercice("I Was Told There Would Be No Math") {

    // You need to wrap a bunch of boxes, but you are running low on
    // wrapping paper, and so you need to submit an order for more.
    //
    // You have a list of the dimensions (length `l`, width `w`, and height `h`)
    // of each box, and only want to order exactly as much as they need.
    //
    // Calculating the required wrapping paper for each box is easy: find the
    // surface area of the box, which is `2*l*w + 2*w*h + 2*h*l`.
    //
    // You also need a little extra paper for each box: the area of the smallest side.

    def wrappingPaperToOrder(boxes: String): Int = ???

    wrappingPaperToOrder("2x3x4") should be (58)
    wrappingPaperToOrder("1x1x10") should be (43)
    wrappingPaperToOrder("2x4x9,1x7x7,10x8x3") should be (557)

    // Can you handle badly formatted input?
    wrappingPaperToOrder("2x3x4,7x7y7z,1x1x10") should be (101)

  }

  exercice("I Was Told There Would Be No Puzzles") {

    // You need to extract strings of a text files which
    // have the following properties:
    //
    // 1) It contains a pair of any two letters that appears at least twice in
    //    the string without overlapping, like xyxy (xy) or aabcdefgaa (aa),
    //    but not like aaa (aa, but it overlaps).
    // 2) It contains at least one letter which repeats with exactly one letter
    //    between them, like xyx, abcdefeghi (efe), or even aaa.

    def filterTextFile(str: String): List[String] = ???

    filterTextFile(
      """
        |ieodomkazucvgmuy
        |qjhvhtzxzqqjkmpb
        |uurcxstgmygtbstg
        |xxyxx
      """.trim.stripMargin
    ) should be (
      List("qjhvhtzxzqqjkmpb", "xxyxx")
    )
  }

}
