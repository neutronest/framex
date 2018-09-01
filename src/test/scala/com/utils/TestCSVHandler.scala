/*
 * Copyright (c) 2018.
 */

package com.utils

import org.scalatest.{FlatSpec, Matchers}
import com.framex.utils.CSVHandler

class TestCSVHandler extends FlatSpec with Matchers  {

  it should "be get data from list" in {

    val csvPath = getClass.getResource("/test.csv").getPath

    val csvSource = CSVHandler.getSourceFromCSV(csvPath)
    val csvHeader = CSVHandler.getHeaderFromSource(csvSource)
    val csvData = CSVHandler.getDataFromSource(csvSource, header = true)

    println(csvHeader)
    csvData.foreach(lineData => println(lineData))
  }

}
