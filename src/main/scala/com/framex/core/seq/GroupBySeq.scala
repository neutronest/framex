/*
 * Copyright (c) 2018.
 */

package com.framex.core.seq

import com.framex.core.elem.{FElem, GroupByElem}

case class GroupBySeq[A](title: String, groupByVec: Vector[GroupByElem[A]]) extends FSeq[A] {

  def map(): Map[Vector[FElem[_]], DataSeq[_]] = {
    this.groupByVec.groupBy(groupByElem => groupByElem.key).map(x => {
      (x._1,  DataSeq(title, x._2.map(_.data)))
    })
  }

//  def reduce[_](f: (DataSeq[_], DataSeq[_]) => DataSeq[_]) : Any = {
//    this.map().reduce(f)
//  }
}
