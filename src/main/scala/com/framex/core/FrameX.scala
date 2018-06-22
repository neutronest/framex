
/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.utils.FrameErrorMessages

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

class FrameX(var data: Vector[Vector[ElemX]], var columnMap: Map[String, Int] = Map()) {

  // used in FrameStats!
  var aggMap = mutable.Map[String, Map[String, Int]]()

  def columnNames = {
    this.columnMap.toSeq.sortWith(_._2 < _._2).map(_._1).toList
  }

  def shape(): (Int, Int) = {
    (data(0).size, data.size)
  }

  def ndim = 2

  def head(n: Int = 5) : FrameX  = FrameX(data.map(c => c.slice(0, n)), this.columnNames)


  def tail(n: Int = 5) : FrameX = FrameX(data.map(c => c.slice(c.size - n, c.size)), this.columnNames)

  def :: = 0 to data(0).size

  def loc(index: Range, columns: List[String]): FrameX = {
    val d: Vector[Vector[ElemX]] = columns.map { c =>
      columnMap.get(c) match {
        //note that contrary to usual python slices, both the start and the stop are included!
        case Some(columnIdx) => data(columnIdx).slice(index.start, index.end + 1)
        case None => throw new Exception(FrameErrorMessages.INDEX_OUT_OF_SIZE)
      }
    }
      .toVector
    FrameX(d, columns)
  }

  def loc(index: Range, columnName: String): FrameX = {
    columnMap.get(columnName) match {
      case Some(columnIdx) =>
        //note that contrary to usual python slices, both the start and the stop are included!
        val series: Vector[ElemX] = data(columnIdx).slice(index.start, index.end + 1)
        new FrameX(Vector(series))
      case None => throw new Exception(FrameErrorMessages.INDEX_OUT_OF_SIZE)
    }
  }


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

  def append(that: FrameX): FrameX = {
    if (this.columnMap != that.columnMap) {
      throw new Exception(FrameErrorMessages.COLUMN_NAMES_MISMATCH)
    }

    FrameX(this.data.zip(that.data).map(x => x._1 ++ x._2), this.columnNames)
  }



  def groupBy(columnNames: List[String]): Option[GroupByFrameX] = {

    val groupbyData : Map[Vector[ElemX], FrameX] = Map[Vector[ElemX], FrameX]()
    val columnIndexs : List[Int] = columnNames.flatMap(this.columnMap.get)
    var dataMap = mutable.Map[String, FrameX]()
    // TODO
    val foobarMap = this.data.transpose.groupBy(record => (columnIndexs.map(record(_))).toString())


    this.data.transpose.groupBy( (record: Vector[ElemX]) => (columnIndexs.map(record(_))).toString())
      .foreach(kv => dataMap += (kv._1 -> FrameX(kv._2.transpose, this.columnNames))
      )
    Some(new GroupByFrameX(dataMap))
  }

  def groupBy(columnName: String) : Option[GroupByFrameX] = {

    this.groupBy(List(columnName));
  }

  def prettyPrint() : Unit = {

    val columnWidths: ListBuffer[Int] = new ListBuffer[Int]
    List.range(0, this.data.length).map(colIdx => {
      val col = this.data(colIdx)
      val rowDataMaxLength = col.map(x => x.elem.toString.length).max
      columnWidths.append(math.max(rowDataMaxLength, columnNames(colIdx).length))
    })

    val sb : StringBuilder = new StringBuilder()
    // print header
    for ( i <- 0 to this.data.length-1) {
      sb.append(" | ")
      sb.append(columnNames(i) + " " * (columnWidths(i)  - columnNames(i).length))
    }
    val allLength = sb.length
    sb.append("\n")
    sb.append("-" * allLength + "\n")

    val dfHead = this.head()
    for (rowNum <- 0 to dfHead.data(0).length-1) {

      val rowData = dfHead.data.map{_(rowNum)}
      for ( (elemX, idx) <- rowData.view.zipWithIndex) {
        sb.append(" | ")
        sb.append(elemX.elem.toString + " " * (columnWidths(idx) - elemX.elem.toString.length ))
      }
      sb.append("\n")
    }
    sb.toString().split("\n").foreach(println)
  }

  def sameElements(that: FrameX): Boolean = {

    if (this.columnMap != that.columnMap) {
      return false
    }

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
      throw new Exception(FrameErrorMessages.COLUMN_SIZE_MISMATCH)
    }
    new FrameX(data.map(_.map(ElemX.wrapper)))
  }

  def apply(data: Vector[Vector[_]], columns: List[String])(implicit ct: ClassTag[ElemX]): FrameX = {
    if (data.size != columns.size) {
      throw new Exception(FrameErrorMessages.COLUMN_SIZE_MISMATCH)
    }

    val x = FrameX(data)
    x.columnMap = columns.zipWithIndex.toMap
    x
  }

  def apply(data_ : Vector[ElemX]): FrameX = {
    val newFrame = Vector()
    new FrameX(newFrame :+ data_)
  }

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