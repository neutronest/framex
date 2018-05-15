package com.framex.core
import java.util.concurrent.atomic.AtomicInteger

import com.framex.core.Expr.Expr

class SeqX[A] extends Seq[ElemX[A]] {

  var data: Vector[ElemX[A]] = Vector()

//  override def data(): Vector[ElemX[A]] = {
//
//
//
//  }

//  override def ndim: Int = ???
//
//  override def shape: List[Int] = ???

  def fromList(l: List[A]): Vector[ElemX[A]] = {

    // TODO: need optimized
    var elemList : Vector[ElemX[A]] = l.map(item => new ElemX[A](ElemX.from(item))).toVector
    this.data = this.data ++ elemList
    this.data
  }

  override def length: Int = {
    this.data.size
  }

//  override def apply(idx: Int): A = ???
//
  
  override def apply(idx: Int): ElemX[A] = ???

  override def iterator: Iterator[ElemX[A]] = {
    this.data.iterator
  }
}
