package com.framex.core
import java.util.concurrent.atomic.AtomicInteger

import com.framex.core.Expr.Expr

class SeqX[A](var data : Vector[ElemX[A]]) extends Seq[ElemX[A]] {

//  override def ndim: Int = ???
//
//  override def shape: List[Int] = ???

  def fromList(l: List[A]): Vector[ElemX[A]] = {


    this.data
  }

  override def length: Int = {
    this.data.size
  }

  def ndim: Int = 1

  def shape : (Int, Int) = {
    (1, length)
  }

//  override def apply(idx: Int): A = ???
//

  override def apply(idx: Int): ElemX[A] = data.apply(idx)

  override def iterator: Iterator[ElemX[A]] = {
    this.data.iterator
  }
}

object SeqX {

  @inline
  def apply[A] (l : List[A]): SeqX[A] = {
    // TODO: need optimized
    var elemList : Vector[ElemX[A]] = l.map(item => new ElemX[A](ElemX.from(item))).toVector
    SeqX(elemList)
  }

  def apply[A] (data_ : Vector[ElemX[A]]): SeqX[A] = {
    new SeqX(data_)
  }

}
