/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.utils.FrameErrorMessages
import shapeless.HList

import scala.collection.mutable.ListBuffer

sealed trait FrameX {
  def shape : (Int, Int)

  def head(n : Int) : FrameX
  
  def tail(n: Int) : FrameX

  def loc(index: Range, columns: Range) : FrameX

  def loc(index: Range, columns: List[String]) : FrameX

  def append(that: FrameX) : FrameX

  def groupBy(columnNames: List[String]) : FrameX
  
  def groupBy(columnName : String) : FrameX
}
case class FrameXData(data: Vector[ColumnX[_]]) extends FrameX {
  def shape : (Int, Int) = ???

  def head(n : Int) : FrameX = ???
  
  def tail(n: Int) : FrameX = ???

  def loc(index: Range, columns: Range) : FrameX = ???

  def loc(index: Range, columns: List[String]) : FrameX = ???

  def append(that: FrameX) : FrameX = ???

  def groupBy(columnNames: List[String]) : FrameX = ???
  
  def groupBy(columnName : String) : FrameX = ???
}
case class FrameXGroupBy(data: Map[String, Vector[ColumnX[_]]]) extends FrameX {
  def shape : (Int, Int) = ???

  def head(n : Int) : FrameX = ???
  
  def tail(n: Int) : FrameX = ???

  def loc(index: Range, columns: Range) : FrameX = ???

  def loc(index: Range, columns: List[String]) : FrameX = ???

  def append(that: FrameX) : FrameX = ???

  def groupBy(columnNames: List[String]) : FrameX = ???
  
  def groupBy(columnName : String) : FrameX = ???
}
case class FrameXMap[A, B](func: A => B, data: FrameX) extends FrameX {
  def shape : (Int, Int) = ???

  def head(n : Int) : FrameX = ???
  
  def tail(n: Int) : FrameX = ???

  def loc(index: Range, columns: Range) : FrameX = ???

  def loc(index: Range, columns: List[String]) : FrameX = ???

  def append(that: FrameX) : FrameX = ???

  def groupBy(columnNames: List[String]) : FrameX = ???
  
  def groupBy(columnName : String) : FrameX = ???
}

object FrameX { 

  def applyMap[A, B](fa: FrameX)(f: A => B): FrameX = {

    val columnXFunctor = ColumnX.columnXFunctor
    fa match {
      case FrameXData(data) => {
        var ll = new ListBuffer[ColumnX[B]]()
        data.foreach(column => {
          column match {
            case ColumnXData(title, data: Vector[A]) =>
              val col = columnXFunctor.map(ColumnXData(title, data: Vector[A]))(f)
              ll += col
            case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
          }
        })
        FrameXData(ll.toVector)
      }
      case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
    }
  }
}
