/*
 * Copyright (c) 2018.
 */
package com.framex.core

import com.framex.utils.FrameErrorMessages
import scalaz.{Functor, Monad}

sealed trait ColumnX[A]
case class ColumnXData[A](title: String, data: Vector[A]) extends ColumnX[A]
case class ColumnXMap[A, B](fa: ColumnX[A], func: A => B) extends ColumnX[B]

object ColumnX {

  implicit val columnXFunctor = new Functor[ColumnX] {
    override def map[A, B](fa: ColumnX[A])(f: A => B): ColumnX[B] = {

      fa match {
        case ColumnXData(title, data: Vector[A]) => ColumnXData(title, data.map(f))
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }
  }

//  implicit val columnXMonad = new Monad[ColumnX] {
//    override def bind[A, B](fa: ColumnX[A])(f: A => ColumnX[B]): ColumnX[B] = ???
//
//    override def point[A](a: => A): ColumnX[A] = ???
//  }
}
