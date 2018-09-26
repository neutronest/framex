/*
 * Copyright (c) 2018.
 */

package com.framex.core.archive

import scala.collection.mutable

class GroupByFrameX(var dataMap: mutable.Map[String, FrameX2]) {

  import com.framex.stats.Stats._
  def agg(opName: String) : GroupByFrameX = {
    new GroupByFrameX(dataMap.map(kv => (kv._1 -> kv._2.agg(opName))))
  }

  def agg(opNames: List[String]) : GroupByFrameX = {
    new GroupByFrameX(dataMap.map(kv => (kv._1 -> kv._2.agg(opNames))))
  }

  def agg(opDict: Map[String, List[String]]) : GroupByFrameX = {
    new GroupByFrameX(dataMap.map(kv => (kv._1 -> kv._2.agg(opDict))))
  }
}

object GroupByFrameX {
}