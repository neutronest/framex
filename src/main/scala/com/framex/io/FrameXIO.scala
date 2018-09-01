/*
 * Copyright (c) 2018.
 */

package com.framex.io
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import com.framex.core.{ElemX, FrameX}
import com.framex.utils.FrameErrorMessages
import com.framex.utils.CSVHandler

import scala.collection.mutable.ListBuffer


object FrameXIO {


  def readCSV(fileName: String,
              sep: String) : FrameX = {


    val bufferedSource = io.Source.fromFile(fileName)
    val headerLine = bufferedSource.getLines.next()
    val columnNames = headerLine.split(sep + CSVHandler.quotation_regex, -1).map(_.trim).toList
    var rowChecker : ListBuffer[String] = ListBuffer()

    var data : ListBuffer[List[ElemX]] = ListBuffer()
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(sep + CSVHandler.quotation_regex, -1)
        .map(elem => ElemX.wrapperDoubleOrString(elem.trim()))

     if (data.length == 0) {
       cols.foreach(elemx => {
         data += List(elemx)
         rowChecker += elemx.dtype
       })
       println(rowChecker)
     } else {
       (rowChecker.toList zip cols).foreach(zipChecker => {
         val checkType = zipChecker._1
         val elemType = zipChecker._2.dtype
         if (elemType != checkType) {
           throw new Exception(FrameErrorMessages.COLUMN_TYPE_MISMATCH)
         }
       })

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
