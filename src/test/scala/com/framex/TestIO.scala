/*
 * Copyright (c) 2018.
 */

package com.framex
import com.framex.core.Expr.ExDouble
import com.framex.core.{ElemX, FrameX}
import com.framex.io.FrameXIO
import scala.io.Source
import org.scalatest._
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

class TestIO extends FlatSpec with Matchers {

  it should "read from ND4J" in {
    val arr1: INDArray = Nd4j.create(Array[Float](1, 2, 3, 4), Array[Int](2, 2))
    val df = FrameXIO.readND4J(arr1)
    df.equals(FrameX(
      Vector(
        Vector(ElemX(ExDouble(1)), ElemX(ExDouble(3))),
        Vector(ElemX(ExDouble(2)), ElemX(ExDouble(4)))
      )
    )) shouldEqual(true)
  }

  it should "read from csv" in {
    val csvPath = getClass.getResource("/test.csv").getPath
    val df = FrameXIO.readCSV(csvPath,
    ",", "")
    df.prettyPrint()
  }
}
