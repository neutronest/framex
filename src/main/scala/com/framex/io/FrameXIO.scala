/*
 * Copyright (c) 2018.
 */

package com.framex.io
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import com.framex.core.{ElemX, FrameX}


object FrameXIO {

  def readCSV(fileName: String,
              sep: String,
              header: String
             ) : FrameX = {

    ???
  }

  def toCSV(df: FrameX, fileName: String) : Unit = {
    ???
  }

  def readND4J(nd4jData: INDArray) : FrameX = {
    val data = nd4jData.transpose().toDoubleMatrix().map(f => f.map(ElemX.wrapper).toVector).toVector
    FrameX(data)
  }

  def toND4J(df: FrameX) : Any = {
    ???
  }


}
