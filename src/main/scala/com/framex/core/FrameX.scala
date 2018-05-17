package com.framex.core

import com.framex.core.Expr.{BottomType, ExType, Nan}

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

class FrameX(var data: Vector[Vector[ElemX]]) {



  def shape() : (Int, Int) = {
    (data(0).size, data.size)
  }

  def ndim = 2

  def apply(rowIdx: Int) : Vector[ElemX] = {

    var row = new ListBuffer[ElemX]()
    data.foreach(seq => {
      row += seq(rowIdx)
    })
    row.toVector
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
    var foobar : Vector[Vector[ElemX]] = ll.map(
      l => l.map(
        item => {
         item match {
            case item: Int  => ElemX(item.asInstanceOf[Int])
            case item: Double => ElemX(item.asInstanceOf[Double])
            case item: String => ElemX(item.asInstanceOf[String])
            case _ => ElemX(Nan)

          }
        }).toVector)
      .toVector
    apply(foobar)

  }

}