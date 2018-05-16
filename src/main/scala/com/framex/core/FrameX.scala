package com.framex.core

import com.framex.core.Expr.BottomType

import scala.reflect.ClassTag


class FrameX()(var data: Vector[Vector[ElemX]]) {
}

object FrameX {
  def apply(data_ : Vector[Vector[ElemX]]): FrameX = {
    FrameX(data_)
  }

  def applyOne[A](data_ : Vector[ElemX]): FrameX = {

    var frame = Vector()
    FrameX(frame :+ data_)
  }

  def fromList[A: BottomType](ll: List[List[A]])(implicit elemCT: ClassTag[ElemX]): FrameX = {
    var foobar : Vector[Vector[ElemX]] = ll.map(
      l => l.map(
      item => ElemX(item)).toVector)
      .toVector
    apply(foobar)
  }


}