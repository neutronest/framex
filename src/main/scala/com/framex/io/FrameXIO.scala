/*
 * Copyright (c) 2018.
 */

package com.framex.io
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import com.framex.core.{ElemX, FrameX}

import scala.collection.mutable.ListBuffer


object FrameXIO {



  def readCSV(fileName: String,
              sep: String) : FrameX = {


    val bufferedSource = io.Source.fromFile(fileName)
    val headerLine = bufferedSource.getLines.next()
    val columnNames = headerLine.split(sep + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim).toList
    println("csv header size")
    println(columnNames.length)

    var data : ListBuffer[List[ElemX]] = ListBuffer()
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(sep + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(elem => ElemX.wrapperDoubleOrString(elem.trim()))
      if (data.length == 0) {
        cols.foreach(elemx => {
          data += List(elemx)
        })
      } else {
        data.foreach( columnData => {

        })
        data = (data zip cols).map( zipData => zipData._1++List(zipData._2))
      }

    }
    FrameX(data.toList, columnNames)
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
