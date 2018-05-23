package com.framex

import org.scalatest._
import com.framex.core.FrameX

class TestFrameX extends FlatSpec with Matchers {

  it should "init FrameX from two dimensional list" in {

    var ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val df = FrameX.fromList(ll)
    df.data.foreach(f => f.foreach(
      f2 => println(f2.elem)
    ))

    val ll2 = List(List(3) ,List("C"), List("2016-03-03"))
    val ll24 = List(List(3 , 4, 5), List("C", "D", "E"), List("2016-03-03","2011-02-02", "2017-02-12"))
    val df2 = df(2)
    df(2).equals(FrameX.fromList(ll2)) shouldEqual true
    df(2, 4).equals(FrameX.fromList(ll24)) shouldEqual true

    val badLL = List(List(1,2,3), List("C", "D"))
    val thrown = intercept[Exception] {FrameX.fromList(badLL)}
    thrown.getMessage shouldEqual("COLUMNS' SIZE MUST SAME!")

  }

  it should "shape" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val df = FrameX.fromList(ll)
    df.shape().equals((5, 3)) shouldEqual true
  }

  it should "test column name" in {
    val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
    val columnNames = List("id", "word", "date")
    val df = FrameX.fromList(ll, columnNames)
    df("date").data.foreach(f => f.foreach(f2 => println(f2.elem)))
    df("date").equals(FrameX.fromList(List(List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12")))) shouldEqual(true)
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
