package com.framex.core
import com.framex.core.Expr._

class ElemX(var elem : ExType) {

  def equals(that: ElemX): Boolean = {
    this.elem.equals(that.elem)
  }
}

object ElemX {

  def apply(elem_ : ExType) : ElemX = {
    new ElemX(elem_)
  }

  def apply[A: BottomType](data: A) : ElemX = {
   ElemX(ExType(data))
  }


}