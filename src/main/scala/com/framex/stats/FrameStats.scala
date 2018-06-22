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

    def agg(opMap: Map[String, List[String]]) : FrameX = {
      var data = Vector[Vector[ElemX]]()
      var aggMap = mutable.Map[String, Map[String, Int]]()
      var aggNames = List[String]()
      var columnIndexAcc = 0
      opMap.foreach(kv => {
        val (columnName, opNames) = (kv._1, kv._2)
        var eachMapforColumn = Map[String, Int]()
        df.columnMap.get(columnName) match  {
          case None => throw new Exception(FrameErrorMessages.COLUMN_NAMES_NOT_FOUND)
          case Some(columnIndex) => {
            val columnData = df.data(columnIndex)
            opNames.foreach(opName => {
              val vectorDataApplyByOp = Vector(getBasicStatsOp(opName).apply(columnData))
              eachMapforColumn += (opName -> columnIndexAcc)
              columnIndexAcc += 1
              data :+= vectorDataApplyByOp
            })
          }
        }
        aggMap += (columnName -> eachMapforColumn)

      })
      val dfAgg = FrameX(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }

    def getBasicStatsOp(op: String) : (Vector[ElemX] => ElemX) = {

      import com.framex.`implicit`.VectorElemXOps._

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
        case "mean" => {
          data: Vector[ElemX] => data.average()
        }
        case "median" => {
          data: Vector[ElemX] => data.median()
        }
        case _ => {
          throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
        }
      }
    }
  }
}

