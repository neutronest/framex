
/*
 * Copyright (c) 2018.
 */

package com.framex.core

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

class FrameX(var data: Vector[Vector[ElemX]], var columnMap: Map[String, Int] = Map()) {

  def shape(): (Int, Int) = {
    (data(0).size, data.size)
  }

  def head(n: Int = 5) : FrameX  = FrameX(data.map(c => c.slice(0, n)))

  def tail(n: Int = 5) : FrameX = FrameX(data.map(c => c.slice(c.size - n, c.size)))

  def :: = 0 to data(0).size

  def loc(index: Range, columns: List[String]): FrameX = {
    val d: Vector[Vector[ElemX]] = columns.map { c =>
      columnMap.get(c) match {
        case Some(columnIdx) => data(columnIdx).slice(index.start, index.end + 1) //note that contrary to usual python slices, both the start and the stop are included!
        case None => throw new Exception("out of size")
      }
    }
      .toVector
    FrameX(d, columns)
  }

  def loc(index: Range, columnName: String): FrameX = {
    columnMap.get(columnName) match {
      case Some(columnIdx) =>
        val series: Vector[ElemX] = data(columnIdx).slice(index.start, index.end + 1) //note that contrary to usual python slices, both the start and the stop are included!
        new FrameX(Vector(series))
      case None => throw new Exception("out of size")
    }
  }

  def ndim = 2

  def apply(rowIdx: Int): FrameX = {
    val row = new ListBuffer[Vector[ElemX]]()
    data.foreach(seq => {
      row += Vector(seq(rowIdx))
    })
    new FrameX(row.toVector)
  }

  def apply(rowFrom: Int, rowTo: Int): FrameX = {
    val row = new ListBuffer[Vector[ElemX]]()
    data.foreach(seq => {
      row ++= Vector(seq.slice(rowFrom, rowTo))
    })
    new FrameX(row.toVector)
  }

  def sameElements(that: FrameX): Boolean = {
    val thisEachCol = this.data.iterator
    val thatEachCol = that.data.iterator
    while (thisEachCol.hasNext && thatEachCol.hasNext) {
      val theseElem = thisEachCol.next.iterator
      val thoseElem = thatEachCol.next.iterator
      while (theseElem.hasNext && thoseElem.hasNext) {
        if (!theseElem.next.elem.equals(thoseElem.next.elem)) {
          return false
        }
      }
      !theseElem.hasNext && !thoseElem.hasNext
    }
    !thisEachCol.hasNext && !thatEachCol.hasNext
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: FrameX => this sameElements that
    case _ => false
  }

  override def hashCode(): Int = super.hashCode()
}

object FrameX {

  def apply(data: Vector[Vector[_]])(implicit ct: ClassTag[ElemX]): FrameX = {
    val lenOfCol = data.map(_.size)
    if (lenOfCol.distinct.size != 1) {
      throw new Exception("COLUMNS' SIZE MUST SAME!")
    }
    new FrameX(data.map(_.map(ElemX.wrapper)))
  }

  def apply(data: Vector[Vector[_]], columns: List[String])(implicit ct: ClassTag[ElemX]): FrameX = {
    if (data.size != columns.size) {
      throw new Exception("column_names' size is not equal to real data size")
    }

    val x = FrameX(data)
    x.columnMap = columns.zipWithIndex.toMap
    x
  }

  def apply(data_ : Vector[ElemX]): FrameX = {
    val newFrame = Vector()
    new FrameX(newFrame :+ data_)
  }

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

  def apply(ll: List[List[_]])(implicit ct: ClassTag[ElemX]): FrameX = {
    val lenOfCol = ll.map(_.size)
    if (lenOfCol.distinct.size != 1) {
      throw new Exception("COLUMNS' SIZE MUST SAME!")
    }
    FrameX(ll.map(_.toVector).toVector)
  }

  def apply(ll: List[List[_]], columns: List[String]): FrameX = {
    if (ll.size != columns.size) {
      throw new Exception("column_names' size is not equal to real data size")
    }
    val frameX = FrameX(ll)
    frameX.columnMap = columns.zipWithIndex.toMap
    frameX
  }
}