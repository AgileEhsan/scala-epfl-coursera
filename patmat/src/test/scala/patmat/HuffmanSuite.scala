package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 =
      Fork(
        Leaf('a', 2),
        Leaf('b', 3),
        List('a', 'b'),
        5)

    val t2 =
      Fork(
        Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5),
        Leaf('d', 4),
        List('a', 'b', 'd'),
        9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("decode exo tree") {
    new TestTrees {
      val tcd = Fork(Leaf('c', 1), Leaf('d', 1), "cd".toList, 2)
      val tbcd = Fork(Leaf('b', 3), tcd, "bcd".toList, 5)
      val tef = Fork(Leaf('e', 1), Leaf('f', 1), "ef".toList, 2)
      val tgh = Fork(Leaf('g', 1), Leaf('h', 1), "gh".toList, 2)
      val tefgh = Fork(tef, tgh, "efgh".toList, 4)
      val tbcdefgh = Fork(tbcd, tefgh, "bcdefgh".toList, 9)
      val tree_abcdefgh = Fork(Leaf('a', 8), tbcdefgh, "abcdefgh".toList, 17)
      val tree_build = createCodeTree("abcdefgh".toList)
      println(tree_abcdefgh)
      val text = decode(tree_abcdefgh, List(1, 0, 0, 0, 1, 0, 1, 0))

      assert(decode(tree_abcdefgh, List(0)) === "a".toList) //a
      assert(decode(tree_abcdefgh, List(1, 0, 1, 1)) === "d".toList) //d
      assert(text == "bac".toList) //bac

      //println(tree_build)
      //assert(tree_abcdefgh === tree_build)
    }
  }

  test("encode exo tree") {
    new TestTrees {
      val tcd = Fork(Leaf('c', 1), Leaf('d', 1), "cd".toList, 2)
      val tbcd = Fork(Leaf('b', 3), tcd, "bcd".toList, 5)
      val tef = Fork(Leaf('e', 1), Leaf('f', 1), "ef".toList, 2)
      val tgh = Fork(Leaf('g', 1), Leaf('h', 1), "gh".toList, 2)
      val tefgh = Fork(tef, tgh, "efgh".toList, 4)
      val tbcdefgh = Fork(tbcd, tefgh, "bcdefgh".toList, 9)
      val tree_abcdefgh = Fork(Leaf('a', 8), tbcdefgh, "abcdefgh".toList, 17)
      val tree_build = createCodeTree("abcdefgh".toList)
      println(tree_abcdefgh)
      val text = decode(tree_abcdefgh, List(1, 0, 0, 0, 1, 0, 1, 0))

      val e_a = encode(tree_abcdefgh)("a".toList)
      val e_d = encode(tree_abcdefgh)("d".toList)
      val e_bac = encode(tree_abcdefgh)("bac".toList)

      println(e_a)
      println(e_d)
      println(e_bac)

      //assert(e_a === "0".toList)
      //assert(e_d === "1011".toList)
      //assert(e_bac === "10001010".toList)
      
      println(decodedSecret)
      
    }
  }
}
