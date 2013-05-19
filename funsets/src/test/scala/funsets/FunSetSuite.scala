package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val s8 = singletonSet(8)

  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
      assert(!contains(s1, 3), "Singleton")

    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains if in both set") {
    new TestSets {
      val set1 = union(s1, s2)
      val set2 = union(s1, s3)
      val set3 = union(union(s1, s2), s3);

      val result = intersect(set1, set2)
      val res1 = intersect(set3, set1)
      val res2 = intersect(set3, set2)

      assert(contains(result, 1), "Intersect 1")
      assert(!contains(result, 2), "Intersect 2")
      assert(!contains(result, 3), "Intersect 3")

      assert(contains(res1, 1), "")
      assert(contains(res1, 2), "")
      assert(!contains(res1, 3), "")

    }
  }

  test("diff contains if not in the other") {
    new TestSets {

      val set1 = union(s1, s2)
      val set2 = union(s1, s3)
      val set3 = union(set1, s3);
      val set4 = union(set3, s4)

      val res1 = diff(set3, set1)
      val res2 = diff(set3, set2)

      assert(contains(res1, 3), "contains 3")
      assert(!contains(res1, 1), "contains 3")
      assert(!contains(res1, 2), "contains 3")

      assert(contains(res2, 2), "contains 2")
      assert(!contains(res2, 1), "contains 2")
      assert(!contains(res2, 3), "contains 2")

    }
  }

  test("filter contains the subset of `s` for which `p` holds") {
    new TestSets {

      val set1 = union(s1, s2)
      val set2 = union(s1, s3)
      val set3 = union(set1, s3);
      val set4 = union(set3, s4)

      def sup2(x: Int): Boolean = x > 2

      val res1 = filter(set4, sup2)
      val res2 = filter(set3, sup2)

      assert(contains(res1, 3), "contains 3 & 4")
      assert(contains(res1, 4), "contains 3 & 4")
      assert(!contains(res1, 1), "contains 3 & 4")
      assert(!contains(res1, 2), "contains 3 & 4")

      assert(contains(res2, 3), "contains 3")
      assert(!contains(res2, 1), "contains 3")
      assert(!contains(res2, 2), "contains 3")
      assert(!contains(res2, 4), "contains 3")
    }
  }

  test("forall") {
    new TestSets {

      val set1 = union(s1, s2)
      val set2 = union(s1, s3)
      val set3 = union(set1, s3);
      val set4 = union(set3, s4)

      def sup0(x: Int): Boolean = x > 0
      def inf10(x: Int): Boolean = x < 10

      assert(forall(set4, sup0), "all sup 0")
      assert(forall(set4, inf10), "all inf 10")
      assert(!forall(set4, (x: Int) => x != 2), "all diff 2")

    }
  }

  test("exists") {
    new TestSets {

      val set1 = union(s1, s2)
      val set2 = union(s1, s3)
      val set3 = union(set1, s3);
      val set4 = union(set3, s4)

      def sup0(x: Int): Boolean = x > 0
      def inf10(x: Int): Boolean = x < 10

      assert(exists(set4, sup0), "exists sup 0")
      assert(exists(set4, inf10), "exists inf 10")
      assert(exists(set4, (x: Int) => x != 2), "exists diff 2")
      assert(!exists(set4, (x: Int) => x > 6), "exists sup 6")
      
    }
  }

}
