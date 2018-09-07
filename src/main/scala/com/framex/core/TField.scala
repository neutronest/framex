/*
 * Copyright (c) 2018.
 */

package com.framex.core


//object Foo {
//  type ISB = Int :+: String :+: Boolean :+: CNil
//
//}

sealed trait TField[A]
case object None
case class IntField(x: Int) extends TField[Int]
case class DoubleField(x: Double) extends TField[Double]
case class StringField(x: String) extends TField[String]

//object TField {
//
//  import scalaz.Isomorphism._
//
//
//
//  type CoyoTField[A] = Coyoneda[TField, A]
//
//
//  def fmap[A, B](fa: CoyoTField[A])(f: A => B) : CoyoTField[B] = {
//    fa.unlift match {
//      case IntField(x: Int) => fa.map(f)
//      case DoubleField(x: Double) => fa.map(f)
//      case StringField(x: String) => fa.map(f)
//    }
//  }
//
//}

