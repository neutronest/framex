package com.framex.core

import com.framex.core.Expr._

class ElemX[_](var data : ExType[_] = Nan) {
}

object ElemX {

  def apply[A](data:ExType[A]) : ElemX[A] = {
    new ElemX[A](data)
  }

  def apply(data:Any) : ExType[_] = {
    data match {
      case x: Int => ExInt (x)
      case x: Double => ExDouble (x)
      case x: String => ExString (x)
      case _ => Nan
    }
  }

  def init(data_ : ExType[_]) : ElemX[_] = {
    return new ElemX(data_)
  }

  def from (data_ : Any): ExType[_] = {
    data_ match {
      case x: Int => ExInt (x)
      case x: Double => ExDouble (x)
      case x: String => ExString (x)
      case _ => Nan
    }
  }


}