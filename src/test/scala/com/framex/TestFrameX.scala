package com.framex

import java.io.File

import org.scalatest._
import com.framex.core.FrameX
import com.github.tototoshi.csv.CSVWriter

class TestFrameX extends FlatSpec with Matchers {

  "FromList" should "init FrameX from two dimensional list" in {

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
