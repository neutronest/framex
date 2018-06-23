/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core.Expr.{ExDouble, ExInt}
import org.scalatest._
import com.framex.core.{ElemX, FrameX}

import scala.collection.mutable

class TestFrameX extends FlatSpec with Matchers {

  it should "init FrameX from two dimensional list" in {

    var ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val df = FrameX(ll)

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

    FrameX(ll, columnNames).tail(1) shouldBe FrameX(
      Vector(
        Vector(ElemX(5)),
        Vector(ElemX("E")),
        Vector(ElemX("2017-02-12"))
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


  it should "append new frameX to existed frameX" in {

    val ll = List(
      List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"),
      List("Tom", "Axiba Warning", "Dong Chao", "Zhang zhi hao", "zzz"),
      List("Man", "Woman", "Woman", "Man", "Man"))

    val existedLL = List(
      List(1, 2, 3),
      List("A", "B", "C"),
      List("2015-01-10", "2017-08-22", "2016-03-03"),
      List("Tom", "Axiba Warning", "Dong Chao"),
      List("Man", "Woman", "Woman"))

    val appendedLL = List(
      List(4, 5),
      List("D", "E"),
      List("2011-02-02", "2017-02-12"),
      List( "Zhang zhi hao", "zzz"),
      List("Man", "Man")
    )
    val columnNames = List("id", "word", "date", "name" , "gender")

    val existedDf = FrameX(existedLL, columnNames)
    val appendedDf = FrameX(appendedLL, columnNames)
    val df = FrameX(ll, columnNames)
    val calcuatedDf = existedDf.append(appendedDf)
    calcuatedDf.equals(df) shouldEqual(true)
  }

  it should "groupBy one column" in {
    val ll = List(
      List(1, 1, 2, 2),
      List(1, 2, 3, 4),
      List(0.36, 0.22, 1.26, -0.56)
    )
    val columnNames = List("A", "B", "C")
    val df = FrameX(ll, columnNames)
    val testDataMap : Map[String, FrameX] = Map(
      List(ElemX(1)).toString() -> FrameX(List(
        List(1, 1),
        List(1, 2),
        List(0.36, 0.22)
      ), columnNames),
      List(ElemX(2)).toString() -> FrameX(List(
        List(2, 2),
        List(3, 4),
        List(1.26, -0.56)
      ), columnNames)
    )
    df.groupBy("A") match {
      case None => false shouldEqual(true)
      case Some(groupByObj) => {
        groupByObj.dataMap.equals(testDataMap) shouldEqual(true)
      }
    }
  }

  it should "groupBy multi columns" in {
    val ll = List(
      List(1,1,2,2,3,4,5),
      List(1,1,2,2,3,4,5),
      List('a', 'b', 'c', 'd', 'e', 'f', 'g')
    )
    val columnNames = List("a", "b", "c")
    val testedDataMap : Map[String, FrameX] = Map(
      List(ElemX(1), ElemX(1)).toString() -> FrameX(List(
        List(1, 1), List(1, 1), List('a', 'b')
      ), columnNames),
      List(ElemX(4), ElemX(4)).toString() -> FrameX(List(
        List(4), List(4), List('f')
      ), columnNames),
      List(ElemX(3), ElemX(3)).toString() -> FrameX(List(
        List(3), List(3), List('e')
      ), columnNames),
      List(ElemX(2), ElemX(2)).toString() -> FrameX(List(
        List(2, 2), List(2, 2), List('c', 'd')
      ), columnNames),
      List(ElemX(5), ElemX(5)).toString() -> FrameX(List(
        List(5), List(5), List('g')
      ), columnNames)
    )

    val df = FrameX(ll, columnNames)
    df.groupBy(List("a", "b")) match {
      case None => false shouldEqual(true)
      case Some(groupByObj) => {
        groupByObj.dataMap.equals(testedDataMap) shouldEqual(true)
      }
    }
  }

  it should "agg function for all column" in {

    import com.framex.stats.Stats._

    val ll = List(
      List(1, 1, 2, 2),
      List(1, 2, 3, 4),
      List(0.36, 0.22, 1.26, -0.56)
    )
    val columnNames = List("A", "B", "C")
    val df = FrameX(ll, columnNames)
    val dfAgg = df.agg("sum")
    dfAgg.data.equals(Vector(
      Vector(ElemX(ExInt(6))),
      Vector(ElemX(ExInt(10))),
      Vector(ElemX(ExDouble(1.2799999999999998)))

    )) shouldEqual(true)
  }

  it should "agg multiple functions for all column" in {
    import com.framex.stats.Stats._
    val ll = List(
      List(1, 1, 2, 2),
      List(1, 2, 3, 4),
      List(0.36, 0.22, 1.26, -0.56)
    )
    val columnNames = List("A", "B", "C")
    val df = FrameX(ll, columnNames)
    val dfAgg = df.agg(List("sum", "max"))
    dfAgg.data.equals(Vector(
      Vector(ElemX(ExInt(6))),
      Vector(ElemX(ExInt(2))),
      Vector(ElemX(ExInt(10))),
      Vector(ElemX(ExInt(4))),
      Vector(ElemX(ExDouble(1.2799999999999998))),
      Vector(ElemX(ExDouble(1.26)))
    )) shouldEqual(true)
  }

  it should "agg multiple different functions for each column" in {
    import com.framex.stats.Stats._
    val ll = List(
      List(1, 1, 2, 2),
      List(1, 2, 3, 4),
      List(0.36, 0.22, 1.26, -0.56)
    )
    val columnNames = List("A", "B", "C")
    val df = FrameX(ll, columnNames)
    val dfAgg = df.agg(Map(("A" -> List("sum")),
      ("B" -> List("max", "min")),
      ("C" -> List("sum", "max", "min"))))
    dfAgg.data.equals(Vector(
      Vector(ElemX(ExInt(6))),
      Vector(ElemX(ExInt(4))),
      Vector(ElemX(ExInt(1))),
      Vector(ElemX(ExDouble(1.2799999999999998))),
      Vector(ElemX(ExDouble(1.26))),
      Vector(ElemX(ExDouble(-0.56)))
    ))
  }

  it should "agg functions after groupBy FrameX" in {
    val ll = List(
      List(1, 1, 2, 2),
      List(1, 2, 3, 4),
      List(0.36, 0.22, 1.26, -0.56)
    )
    val columnNames = List("A", "B", "C")
    val df = FrameX(ll, columnNames)
    val testDataMap : Map[String, FrameX] = Map(
      List(ElemX(1)).toString() -> FrameX(List(
        List(1, 1),
        List(1, 2),
        List(0.36, 0.22)
      ), columnNames),
      List(ElemX(2)).toString() -> FrameX(List(
        List(2, 2),
        List(3, 4),
        List(1.26, -0.56)
      ), columnNames)
    )

    val frameAfterGroupByA1 = FrameX( List(List(2), List(3), List(0.58)))
    frameAfterGroupByA1.aggMap = mutable.Map(
      ("A" -> Map(
        ("sum" -> 0))
        ),
      ("B" -> Map(
        ("sum" -> 1))
        ),
      ("C" -> Map(
        ("sum" -> 2))
        )
    )

    val frameAfterGroupByA2 = FrameX( List(List(4), List(7), List(0.7)))
    frameAfterGroupByA2.aggMap = mutable.Map(
      ("A" -> Map(
        ("sum" -> 0))
        ),
      ("B" -> Map(
        ("sum" -> 1))
        ),
      ("C" -> Map(
        ("sum" -> 2))
        )
    )

    df.groupBy("A") match {
      case None => true shouldEqual(false)
      case Some(dfGroupByA) => {
        dfGroupByA.agg("sum").dataMap.equals(Map(
          (List(ElemX(1)).toString() -> frameAfterGroupByA1),
          (List(ElemX(2)).toString() -> frameAfterGroupByA2)
        )) shouldEqual(true)
      }
    }
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
