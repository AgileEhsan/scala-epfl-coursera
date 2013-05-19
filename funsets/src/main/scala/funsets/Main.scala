package funsets

object Main extends App {
  import FunSets._

  val s1 = singletonSet(1)
  val s2 = singletonSet(2)
  val s3 = singletonSet(3)
  val s4 = singletonSet(4)
  val s5 = singletonSet(5)
  val s6 = singletonSet(6)
  val s7 = singletonSet(7)
  val s8 = singletonSet(8)

  val set1 = union(s1, s2)
  val set3 = union(set1, s3)
  val set4 = union(set3, s4)
  val set5 = union(set4, s5)
  val set6 = union(set5, s6)
  val set7 = union(set6, s7)
  val set8 = union(set7, s8)

  printSet(set1)
  printSet(set3)
  printSet(set4)
  printSet(set5)
  printSet(set6)
  printSet(set7)
  printSet(set8)
  
  printSet(map(set8, (x:Int) => x * x))
  
  println(contains(singletonSet(1), 1))
}
