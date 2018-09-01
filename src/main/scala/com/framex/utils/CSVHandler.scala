/*
 * Copyright (c) 2018.
 */

package com.framex.utils

import com.google.common.collect.MultimapBuilder.ListMultimapBuilder

import scala.collection.mutable.ListBuffer
import scala.io.BufferedSource

object CSVHandler {

  val quotation_regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"


  def getSourceFromCSV(fileName: String) : BufferedSource = {
    io.Source.fromFile(fileName)
  }

  def getHeaderFromSource(bufferedSource: BufferedSource) : List[String] = {

    val headerLine = bufferedSource.getLines().next()
    headerLine.split(quotation_regex).map(_.trim).toList
  }

  def getDataFromSource(bufferedSource: BufferedSource, header: Boolean) : List[List[String]] = {

    var csvData: ListBuffer[List[String]] = ListBuffer()
//    val bufferSource = io.Source.fromFile(fileName)


    var iter : Iterator[String] = Iterator()
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

  def getColumnBasedData(csvData: List[List[String]]) : List[List[String]] = {

    val csvDataArray = csvData.map(rowData => rowData.toArray).toArray
    val csvDataTArray = csvDataArray.transpose
    csvDataTArray.map(rowData => rowData.toList).toList
  }
}


