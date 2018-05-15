package com.framex.core

import scala.reflect.ClassTag


class FrameX() {

  val data: Vector[Vector[ElemX[_]]] = ???
}

object FrameX {

  def apply(data_ : Vector[Vector[ElemX[_]]]): FrameX = {
    FrameX(data_)
  }

  def applyOne[A](data_ : Vector[ElemX[A]]): FrameX = {

    var frame = Vector()
    FrameX(frame :+ data_)
  }

  def fromList(ll: List[List[_]])(implicit elemCT: ClassTag[ElemX[_]]): FrameX = {


    var baz : List[Vector[ElemX[_]]] = List()
    for (l <- ll) {
      var bb : List[ElemX[_]] = List()
      for (item <- l) {
        bb = bb :+ ElemX(item)
      }
      baz = baz :+ bb.toVector
    }
    apply(baz.toVector)

//    var foobar : Vector[Vector[ElemX[_]]] = ll.map(
//      (l: List[Any]) => l.map(
//      item => new ElemX[_](ElemX.from(item))).toVector)
//      .toVector
//    apply(foobar)
  }


}