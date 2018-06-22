/*
 * Copyright (c) 2018.
 */

package com.framex.stats

import com.framex.core.{ElemX, FrameX}
import com.framex.utils.FrameErrorMessages

import scala.collection.mutable
import scala.reflect.internal.util.Collections

object Stats {

  implicit class FrameStats(var df: FrameX)  {

//    var aggMap = mutable.Map[String, Map[String, Int]]()
//    var aggData = Vector(Vector[ElemX]())

    def agg(op: String) : FrameX = {

      var data = Vector[Vector[ElemX]]()
      var aggMap = mutable.Map[String, Map[String, Int]]()
      data = df.data.map( columnData => {
        Vector(getBasicStatsOp(op).apply(columnData))
      })
      df.columnMap.foreach( kv => {
        var itemMap = Map[String, Int]()
        itemMap += (op -> kv._2)
        aggMap += (kv._1 -> itemMap)
      })
      val dfAgg = FrameX(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }

    def agg(ops: List[String]): FrameX = {
      var data = Vector[Vector[ElemX]]()
      var aggMap = mutable.Map[String, Map[String, Int]]()
      data = df.data.map( columnData => {
        ops.map(op => Vector(getBasicStatsOp(op).apply(columnData))).toVector
      }).flatten
      var columnIndex = 0
      df.columnMap.foreach( kv => {
        var itemMap = Map[String, Int]()
        ops.foreach(op => {
          itemMap += (op -> columnIndex)
          columnIndex += 1
        })
        aggMap += (kv._1 -> itemMap)
      })
      val dfAgg = FrameX(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }


    def getBasicStatsOp(op: String) : (Vector[ElemX] => ElemX) = {

      op match {
        case "max" => {
          data : Vector[ElemX] => data.max
        }
        case "min" => {
          data: Vector[ElemX] => data.min
        }
        case "sum" => {
          data: Vector[ElemX] => data.sum
        }
        case _ => {
          throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
        }
      }

    }

//    def statsOps(op: String) : FrameX = {
//      op match {
//        case "min" => {
//
//        }
//      }
//    }
  }
}

