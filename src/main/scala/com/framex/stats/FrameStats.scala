/*
 * Copyright (c) 2018.
 */

package com.framex.stats

import com.framex.core.archive.{ElemX, FrameX2}
import com.framex.utils.FrameErrorMessages

object Stats {

  implicit class FrameStats(var df: FrameX2) {

    def agg(opName: String): FrameX2 = {
      val data = df.data.map(columnData => {
        Vector(getBasicStatsOp(opName).apply(columnData))
      })
      val aggMap = {
        for {
          kv <- df.columnMap
        } yield {
          kv._1 -> (opName -> kv._2)
        }
      }.mapValues(Map(_))
      val dfAgg = FrameX2(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }

    def agg(opNames: List[String]): FrameX2 = {
      val data = df.data.flatMap(columnData => {
        opNames.map(op => Vector(getBasicStatsOp(op).apply(columnData))).toVector
      })
      var columnIndex = 0
      val aggMap = for {
        kv <- df.columnMap
      } yield {
        kv._1 -> {
          for {
            opName <- opNames
          } yield {
            val tmp = opName -> columnIndex
            columnIndex += 1
            tmp
          }
        }.toMap
      }
      val dfAgg = FrameX2(data)
      dfAgg.aggMap = aggMap
      dfAgg
    }

    def agg(opMap: Map[String, List[String]]): FrameX2 = {
      var columnIndexAcc = 0
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
                val tmp = (vectorDataApplyByOp, columnName -> (opName -> columnIndexAcc))
                columnIndexAcc += 1
                tmp
              }
            }
          }
          list
        }
      }.flatten
      val data: Vector[Vector[ElemX]] = iterable.map(_._1).toVector

      val aggMap: Map[String, Map[String, Int]] = iterable.map(_._2).groupBy(_._1).map {
        case (col, list) =>
          col -> list.map(_._2).toMap
      }
      val dfAgg = FrameX2(data)
      dfAgg.aggMap = aggMap
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

