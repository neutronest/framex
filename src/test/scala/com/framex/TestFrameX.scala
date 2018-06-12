/*
 * Copyright (c) 2018.
 */

package com.framex

import org.scalatest._
import com.framex.core.{ElemX, FrameX}

class TestFrameX extends FlatSpec with Matchers {

  it should "init FrameX from two dimensional list" in {

    var ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val df = FrameX(ll)

    //    df.data.foreach(f => f.foreach(
    //      f2 => println(f2.elem)
    //    ))

    val ll2 = List(List(3), List("C"), List("2016-03-03"))
    val ll24 = List(List(3, 4, 5), List("C", "D", "E"), List("2016-03-03", "2011-02-02", "2017-02-12"))
    val df2 = df(2)
    df(2).equals(FrameX(ll2)) shouldEqual true
    df(2, 4).equals(FrameX(ll24)) shouldEqual true

    val badLL = List(List(1, 2, 3), List("C", "D"))
    val thrown = intercept[Exception] {
      FrameX(badLL)
    }
    thrown.getMessage shouldEqual ("COLUMNS' SIZE MUST SAME!")

  }

  it should "get shape (row, col)" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val df = FrameX(ll)
    df.shape().equals((5, 3)) shouldEqual true
  }

  it should "return head" in {

    val ll = List(List.range(1, 27),
      List.range('a', '{'))
    val columnNames = List("id", "word")
    FrameX(ll, columnNames).head() shouldBe FrameX(
      List(
        List(1,2,3,4,5),
        List('a', 'b', 'c', 'd', 'e')
      ), columnNames
    )
  }

  it should "return head n" in {
    val ll = List(List.range(1, 27),
      List.range('a', '{'))
    val columnNames = List("id", "word")
    FrameX(ll, columnNames).head(3) shouldBe FrameX(
      List(
        List(1,2,3),
        List('a', 'b', 'c')
      ),
      columnNames
    )
  }

  it should "return head not overflow" in {
    val ll = List(List.range(1, 6),
      List.range('a', 'f'))
    val columnNames = List("id", "word")
    FrameX(ll, columnNames).head(10) shouldBe FrameX(ll, columnNames)
  }

  it should "return tail" in {
    val ll = List(List(1, 2, 3, 4, 5, 6),
      List("A", "B", "C", "D", "E", "F"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12", "2019-02-12"))
    val columnNames = List("id", "word", "date")
    FrameX(ll, columnNames).tail() shouldBe FrameX(
      Vector
      (
        Vector(ElemX(2), ElemX(3), ElemX(4), ElemX(5), ElemX(6)),
        Vector(ElemX("B"), ElemX("C"), ElemX("D"), ElemX("E"), ElemX("F")),
        Vector(ElemX("2017-08-22"), ElemX("2016-03-03"), ElemX("2011-02-02"), ElemX("2017-02-12"), ElemX("2019-02-12"))
      ),
      columnNames
    )
  }

  it should "return tail n" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    FrameX(ll, columnNames).tail(3) shouldBe FrameX(
      Vector(
        Vector(ElemX(3), ElemX(4), ElemX(5)),
        Vector(ElemX("C"), ElemX("D"), ElemX("E")),
        Vector(ElemX("2016-03-03"), ElemX("2011-02-02"), ElemX("2017-02-12"))
      ),
      columnNames
    )
  }

  it should "return tail not overflow" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    FrameX(ll, columnNames).tail(10) shouldBe FrameX(
      Vector
      (
        Vector(ElemX(1), ElemX(2), ElemX(3), ElemX(4), ElemX(5)),
        Vector(ElemX("A"), ElemX("B"), ElemX("C"), ElemX("D"), ElemX("E")),
        Vector(ElemX("2015-01-10"), ElemX("2017-08-22"), ElemX("2016-03-03"), ElemX("2011-02-02"), ElemX("2017-02-12"))
      ),
      columnNames
    )
  }

  it should "loc(::, List(...))" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    val df = FrameX(ll, columnNames)
    import df.::
    val x = df.loc(::, List("id", "word"))
    x.columnMap shouldEqual Map("id" -> 0, "word" -> 1)
    x.data shouldEqual Vector(
      Vector(ElemX(1), ElemX(2), ElemX(3), ElemX(4), ElemX(5)),
      Vector(ElemX("A"), ElemX("B"), ElemX("C"), ElemX("D"), ElemX("E"))
    )
  }

  it should "loc(1::2,List(...))" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    val df = FrameX(ll, columnNames)
    import com.framex.`implicit`.IndexOps.IntOps
    val x = df.loc(1 :: 2, List("id", "word"))
    x.columnMap shouldEqual Map("id" -> 0, "word" -> 1)
    x.data shouldEqual Vector(
      Vector(ElemX(2), ElemX(3)),
      Vector(ElemX("B"), ElemX("C"))
    )
  }

  it should "loc(::,.)" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    val df = FrameX(ll, columnNames)
    import df.::
    //df.loc(::, "date").data.foreach(f => f.foreach(f2 => println(f2.elem)))
    df.loc(::, "date").equals(FrameX(List(List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12")))) shouldEqual (true)
  }

  it should "loc(1::3,.)" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    val df = FrameX(ll, columnNames)
    import com.framex.`implicit`.IndexOps.IntOps
    val x = df.loc(1 :: 3, "date")
    x.data shouldEqual Vector(
      Vector(ElemX("2017-08-22"), ElemX("2016-03-03"), ElemX("2011-02-02"))
    )
  }

  it should "prettyPrint" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"),
      List("Tom", "Axiba Warning", "Dong Chao", "Zhang zhi hao", "zzz"),
      List("Man", "Woman", "Woman", "Man", "Man"))
    val columnNames = List("id", "word", "date", "name" , "gender")
    val df = FrameX(ll, columnNames)
    df.prettyPrint()

    true shouldEqual(true)
  }

  it should "FrameX with different columnName / columnIndex should not equal" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"),
      List("Tom", "Axiba Warning", "Dong Chao", "Zhang zhi hao", "zzz"),
      List("Man", "Woman", "Woman", "Man", "Man"))
    val columnNames1 = List("id", "word", "date", "name", "gender")
    val columnNames2 = List("id", "words", "date", "names", "man/woman")
    val columnNames3 = List("id", "word", "name", "date", "gender")
    (FrameX(ll, columnNames1) != FrameX(ll, columnNames2)) shouldEqual(true)
    (FrameX(ll, columnNames1) != FrameX(ll, columnNames3)) shouldEqual(true)
  }

  //  "Performance test" should "cost small time" in {
  //
  //    var ll : List[List[Int]] = (for {
  //      ix <- 1 to 1000000
  //    } yield (1 to 100).toList).toList
  //    val df = FrameX.fromList(ll)
  //
  //  }
  //  "write csv" should "OK" in {
  //    var ll : List[List[Int]] = (for {
  //          ix <- 1 to 1000000
  //        } yield (1 to 100).toList).toList
  //    val f = new File("out.csv")
  //    val writer = CSVWriter.open(f)
  //    writer.writeAll(ll)
  //    writer.close()
  //  }

}
