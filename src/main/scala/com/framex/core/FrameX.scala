package com.framex.core

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

class FrameX(var data: Vector[Vector[ElemX]]) {

  var columnMap: mutable.HashMap[String, Int] = mutable.HashMap()

  def shape() : (Int, Int) = {

    (data(0).size, data.size)
  }

  def ndim = 2

  def apply(rowIdx: Int) : FrameX = {

    val row = new ListBuffer[Vector[ElemX]]()
    data.foreach(seq => {
      row += Vector(seq(rowIdx))
    })
    new FrameX(row.toVector)
  }

  def apply(rowFrom: Int, rowTo:Int) : FrameX = {

    val row = new ListBuffer[Vector[ElemX]]()
    data.foreach(seq => {
      row ++= Vector(seq.slice(rowFrom, rowTo))
    })
    new FrameX(row.toVector)
  }

  def apply(columnName: String) : FrameX = {

    columnMap.get(columnName) match {
      case Some(columnIdx) => {
        val series : Vector[ElemX] = data(columnIdx)
        new FrameX(Vector(series))
      }
      case None => throw new Exception("out of size")
    }
  }

  def sameElements(that: FrameX) : Boolean = {
    val thisEachCol = this.data.iterator
    val thatEachCol  = that.data.iterator
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

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: FrameX => this sameElements that
    case _ => false
  }
}

object FrameX {
  def apply(data_ : Vector[Vector[ElemX]])(implicit elemCT: ClassTag[Vector[ElemX]]): FrameX = {
    new FrameX(data_)
  }

  def apply[A](data_ : Vector[ElemX]) : FrameX = {
    val newFrame = Vector()
    new FrameX(newFrame :+ data_)
  }

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

  def fromList(ll: List[List[_]])(implicit ct: ClassTag[ElemX]): FrameX = {

    val lenOfCol = ll.map(_.size)
    if (lenOfCol.distinct.size != 1) {
      throw new Exception("COLUMNS' SIZE MUST SAME!")
    }

    var foobar : Vector[Vector[ElemX]] = ll.map(
      l => l.map(ElemX.wrapper).toVector)
      .toVector
    apply(foobar)
  }

  def fromList(ll: List[List[_]], columns: List[String]): FrameX  = {

    var columnMap : mutable.HashMap[String, Int] = mutable.HashMap()
    if (ll.size != columns.size) {
      throw new Exception("column_names' size is not equal to real data size")
    }

    columns.zipWithIndex.foreach{
      case (column, idx) =>
        columnMap += (column -> idx)
    }

    var frameX = FrameX.fromList(ll)
    frameX.columnMap = columnMap
    frameX


  }
}