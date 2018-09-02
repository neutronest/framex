/*
 * Copyright (c) 2018.
 */

package com.framex.core
import scalaz.Functor

sealed trait TField[M[_], A] extends Functor[TField[M,A]]
case object None extends TField[Nothing] {
  override def map[Nothing](fa: TField[Nothing])(f: Nothing => Nothing): TField[Nothing]
  = None
}
case class IntField(x: Int) extends TField[Int]
case class DoubleField(x: Double) extends TField[Double]
case class StringField(x: String) extends TField[String]