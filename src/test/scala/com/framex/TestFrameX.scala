package com.framex

import org.scalatest._

import com.framex.core.FrameX

class TestFrameX extends FlatSpec with Matchers {

  "FromList" should "init FrameX from two dimensional list" in {

    var ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"))
  }
  
}
