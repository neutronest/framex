
/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.core.Expr._

class ElemX(var elem: ExType) {

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: ElemX => this.elem.equals(that.elem)
    case _ => false
  }

  override def toString = s"ElemX(" + elem.toString + ")"
}

object ElemX {

  def apply(elem_ : ExType): ElemX = {
    new ElemX(elem_)
  }

  def apply[A: BottomType](data: A): ElemX = {
    ElemX(ExType(data))
  }

  def wrapper(data: Any): ElemX = {

    data match {
      case ele: ElemX => ele
      case item: Int => ElemX(item.asInstanceOf[Int])
      case item: Double => ElemX(item.asInstanceOf[Double])
      case item: String => ElemX(item.asInstanceOf[String])
      case item: Char => ElemX(item.asInstanceOf[Char])
      case _ => ElemX(None)
    }
  }


}