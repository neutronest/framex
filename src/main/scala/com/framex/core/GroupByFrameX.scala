/*
 * Copyright (c) 2018.
 */

package com.framex.core

import scala.collection.mutable

class GroupByFrameX(var dataMap: mutable.Map[String, FrameX]) {

//  var aggNames = List()
//
//  def agg(fn: String): GroupByFrameX = {
//    fn match {
//      case "min" => {
//
//        this.dataMap.foreach(kv => {
//          val minData: Vector[ElemX] = kv._2.data.map(columnData => columnData.min)
//          FrameX(minData.map(value => Vector(value)),
//            columns = kv._2.columnNames.map(columnTitle => columnTitle + "-min")
//          )
//        })
//      }
//    }
//  }
//
//  def agg(fns: List[String]) : GroupByFrameX = ???
}

object GroupByFrameX {



}