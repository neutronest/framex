package com.framex.core

import java.util.concurrent.CyclicBarrier

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

  def apply(rowFrom: Int, rowTo:Int) : Vector[ElemX] = {

    var row = new ListBuffer[ElemX]()
    data.foreach(seq => {
      row ++= seq.slice(rowFrom, rowTo)
    })
    row.toVector
  }

//  def apply(rowSlice: (Int, Int), colSlice: (Int, Int)) : Vector[Vector[ElemX]] = {
//
//  }

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
      l => l.map(ElemX.wrapper).toVector)
      .toVector
    apply(foobar)

  }

}