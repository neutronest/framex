/*
 * Copyright (c) 2018.
 */

package com.framex.stats

import com.framex.core.{ElemX, FrameX}
import com.framex.utils.FrameErrorMessages

import scala.collection.{immutable, mutable}

object Stats {

  implicit class FrameStats(var df: FrameX) {

    def agg(op: String): FrameX = {

      var data = Vector[Vector[ElemX]]()
      var aggMap = Map[String, Map[String, Int]]()
      data = df.data.map(columnData => {
        Vector(getBasicStatsOp(op).apply(columnData))
      })
      df.columnMap.foreach(kv => {
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
      var aggMap = Map[String, Map[String, Int]]()
      data = df.data.map(columnData => {
        ops.map(op => Vector(getBasicStatsOp(op).apply(columnData))).toVector
      }).flatten
      var columnIndex = 0
      df.columnMap.foreach(kv => {
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

    def agg(opMap: Map[String, List[String]]): FrameX = {
      var columnIndexAcc = 1
      val iterable = {
        for {
          (columnName, opNames) <- opMap
        } yield {
          val list = df.columnMap.get(columnName) match {
            case None => throw new RuntimeException(FrameErrorMessages.COLUMN_NAMES_NOT_FOUND)
            case Some(columnIndex) => {
              val columnData = df.data(columnIndex)
              for {
                opName <- opNames
              } yield {
                val vectorDataApplyByOp = Vector(getBasicStatsOp(opName).apply(columnData))
                columnIndexAcc += 1
                (vectorDataApplyByOp, columnName -> (opName -> columnIndexAcc))
              }
            }
          }
          list
        }
      }.flatten
      val data: Vector[Vector[ElemX]] = iterable.map(_._1).toVector
      val map = iterable.map(_._2).toMap.mapValues(Map(_))
      val dfAgg = FrameX(data)
      dfAgg.aggMap = map
      dfAgg
    }

    def getBasicStatsOp(op: String): (Vector[ElemX] => ElemX) = {

      import com.framex.`implicit`.VectorElemXOps._

      op match {
        case "max" => {
          data: Vector[ElemX] => data.max
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

