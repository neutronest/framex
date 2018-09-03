/*
 * Copyright (c) 2018.
 */

package com.framex.utils

import scala.collection.mutable.ListBuffer
import scala.io.BufferedSource

object CSVHandler {

  val quotation_regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"


  def getSourceFromCSV(fileName: String): BufferedSource = {
    io.Source.fromFile(fileName)
  }

  def getHeaderFromSource(bufferedSource: BufferedSource): List[String] = {

    val headerLine = bufferedSource.getLines().next()
    headerLine.split(quotation_regex).map(_.trim).toList
  }

  def getDataFromSource(bufferedSource: BufferedSource, header: Boolean = true): List[List[String]] = {

    var csvData: ListBuffer[List[String]] = ListBuffer()
    //    val bufferSource = io.Source.fromFile(fileName)


    var iter: Iterator[String] = Iterator()
    if (header) {
      iter = bufferedSource.getLines().drop(1)
    } else {
      iter = bufferedSource.getLines()
    }

    for (line <- iter) {
      val row = line.split(quotation_regex).map(_.trim).toList
      csvData += row
    }
    csvData.toList
  }

  def getColumnBasedData(csvData: List[List[String]]): List[List[String]] = {

    val csvDataArray = csvData.map(rowData => rowData.toArray).toArray
    val csvDataTArray = csvDataArray.transpose
    csvDataTArray.map(rowData => rowData.toList).toList
  }


  def getDataTypeByColumn(columnData: List[String]): String = {

    // TODO: optimiaze
    val tryToConverts = for {
      elem <- columnData
    } yield {
      try {
        elem.toDouble
        "Y"
      } catch {
        case _ => "N"
      }
    }
    if (tryToConverts.contains("N")) {
      Constants.ELEMX_TYPE_STRING
    } else {
      Constants.ELEMX_TYPE_DOUBLE
    }
  }
}


