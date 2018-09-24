/*
 * Copyright (c) 2018.
 */

package com.framex.core.seq

import com.framex.core.elem.{DataElem, GroupByElem}


trait FSeq[+A]
case class FnSeq[A, B](title: String, fnVec: Vector[A => B]) extends FSeq[B]


//case class FSeq[+A](title: String, data: Vector[FElem[A]]) {
//  def +(other:FSeq[A])(implicit num: Numeric[A]) : FSeq[A] = {
//    FSeq(title, this.data + other.data)
//  }
//}
//
//object FSeq {
//  def plus[A](x: FSeq[A], y: FSeq[A])(implicit num: Numeric[A]) : FSeq[A] = {
//
//    (x, y) match {
//      case (FSeq(xtitle, xdata: Vector[DataElem[A]]), FSeq(ytitle, ydata: Vector[DataElem[A]])) => {
//        FSeq(x.title, xdata.zip(ydata).map(x => x._1 + x._2 ))
//      }
//      case _ => throw new Exception("...")
//    }
//
//  }
//}
