/*
 * Copyright (c) 2018.
 */

package com.framex.stats

import com.framex.core.{ElemX, FrameX}
import com.framex.utils.FrameErrorMessages

object Stats {

  implicit class FrameStats(var df: FrameX) {

    def agg(op: String): FrameX = {
      val data = df.data.map(columnData => {
        Vector(getBasicStatsOp(op).apply(columnData))
      })
      val aggMap = {
        for {
          kv <- df.columnMap
        } yield {
          kv._1 -> (op -> kv._2)
        }
      }.mapValues(Map(_))
      val dfAgg = FrameX(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }

    def agg(ops: List[String]): FrameX = {
      val data = df.data.flatMap(columnData => {
        ops.map(op => Vector(getBasicStatsOp(op).apply(columnData))).toVector
      })
      var columnIndex = 1
      val aggMap = for {
        kv <- df.columnMap
      } yield {
        kv._1 -> {
          for {
            op <- ops
          } yield {
            columnIndex += 1
            op -> columnIndex
          }
        }.toMap
      }
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

