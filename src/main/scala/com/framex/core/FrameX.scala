/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.core.seq.{DataSeq, FSeq, GroupBySeq}
import com.framex.utils.{Constants, FrameErrorMessages}

import scala.reflect.ClassTag

case class FrameX(val data: Vector[FSeq[_]]) {

  def columnNames(): Vector[String] = this.data.map {
    case DataSeq(title, _) => title
    case GroupBySeq(title, _) => title
    case _ => ???
  }

  def head(n: Int = 5) : FrameX = ???

  def tail(n: Int = 5) : FrameX = ???

  def loc() : FrameX = ???

  def applyMap[A, B](f: A => B): FrameX = {
    val coData = this.data.map {
      case dataSeq: DataSeq[A] => {
        val dataSeqFunctor = DataSeq.dataSeqFunctor
        dataSeqFunctor.map(dataSeq)(f)
      }
      case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
    }
    FrameX(coData)
  }

}

