/*
 * Copyright (c) 2018.
 */

package com.framex.core.seq

import com.framex.core.elem.DataElem
import scalaz.Functor


case class DataSeq[A](title: String, dataVec: Vector[DataElem[A]]) extends FSeq[A] {

}

object DataSeq {

  val dataElemFunctor = DataElem.dataElemFunctor

  implicit val dataSeqFunctor = new Functor[DataSeq] {
    override def map[A, B](fa: DataSeq[A])(f: A => B): DataSeq[B] = {
      val coDataVec = fa.dataVec.map( dataElem => dataElemFunctor.map(dataElem)(f))
      DataSeq(fa.title, coDataVec)
    }
  }

}
