
/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.core.Expr.BottomType

import scala.reflect.ClassTag

class SeqX(var data : Vector[ElemX]) extends Seq[ElemX] {

//  override def ndim: Int = ???
//
//  override def shape: List[Int] = ???


  def sameElements(that: SeqX): Boolean = {
    val these = this.iterator
    val those = that.iterator
    while (these.hasNext && those.hasNext)
      if (!these.next.elem.equals(those.next.elem)) {
        return false
      }

    !these.hasNext && !those.hasNext
  }

  override def equals(that: Any): Boolean = that match {
    case that: SeqX => (that canEqual this) && (this sameElements that)
    case _               => false
  }

  override def hashCode(): Int = super.hashCode()

  override def length: Int = {
    this.data.size
  }

  def ndim: Int = 1

  def shape : (Int, Int) = {
    (1, length)
  }

//  override def apply(idx: Int): A = ???
//

  override def apply(idx: Int): ElemX = data.apply(idx)

  def apply(from:Int, to:Int) : SeqX = SeqX(data.slice(from,to))

  override def iterator: Iterator[ElemX] = {
    this.data.iterator
  }
}

object SeqX {

  @inline
  def apply[A: BottomType] (l : List[A]): SeqX = {
    // TODO: need optimized
    var elemList : Vector[ElemX] = l.map(item => ElemX(item)).toVector
    SeqX(elemList)
  }

  def apply(data_ : Vector[ElemX]): SeqX = {
    new SeqX(data_)
  }


  def apply[A : BottomType](data:Vector[A])(implicit tag: ClassTag[A]):SeqX = {
    SeqX(
      data.map(d => ElemX(d))
    )
  }

}
