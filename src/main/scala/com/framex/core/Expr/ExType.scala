
/*
 * Copyright (c) 2018.
 */

package com.framex.core.Expr

sealed trait ExType

case object None extends ExType {
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case None => true
      case _ => false
    }
  }
}
case class ExInt(x: Int) extends ExType {}
case class ExDouble(x: Double) extends ExType {}
case class ExString(x: String) extends ExType {}
case class ExChar(x: Char) extends ExType {}
case class ExCategory(x: List[String]) extends ExType {}
case class ExOther(x: Any) extends ExType {}

object ExType {

  def apply[A: BottomType](data: A): ExType = {
    data match {
      case x: Int => ExInt(x)
      case x: Double => ExDouble (x)
      case x: String => ExString (x)
      case x: Char => ExChar (x)
      case _ => None
    }
  }
//  implicit object ExForNothing extends ExType[Nothing]
//
//  implicit object ExForInt extends ExType[Int]
//
//  implicit object ExForDouble extends ExType[Double]
//
//  implicit object ExForString extends ExType[String]
//
//  implicit object ExForEnum extends ExType[Enumeration]
//
//   def from(x: Int): ExType[BottomType[Int]]  = ExInt(x)
//   def from(x: Double): ExType[BottomType[Double]]  = ExDouble(x)
//   def from(x: String): ExType[BottomType[String]]  = ExString(x)
//   def from(x: Nothing): ExType[BottomType[Nothing]]  = Nan
}



