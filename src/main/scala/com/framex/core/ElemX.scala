
/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.core.Expr._
import com.framex.utils.FrameErrorMessages

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

  trait NumericForElemX extends Numeric[ElemX] with Ordering[ElemX] {
    def plus(x: ElemX, y: ElemX): ElemX = {
      (x.elem, y.elem) match {
        case (ExInt(e1), ExInt(e2)) => ElemX(e1 + e2)
        case (ExDouble(e1), ExDouble(e2)) => ElemX(e1 + e2)
        case (_, _) => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def minus(x: ElemX, y: ElemX): ElemX = {
      (x.elem, y.elem) match {
        case (ExInt(e1), ExInt(e2)) => ElemX(e1 - e2)
        case (ExDouble(e1), ExDouble(e2)) => ElemX(e1 - e2)
        case (_, _) => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def times(x: ElemX, y: ElemX): ElemX = {
      (x.elem, y.elem) match {
        case (ExInt(e1), ExInt(e2)) => ElemX(e1 * e2)
        case (ExDouble(e1), ExDouble(e2)) => ElemX(e1 * e2)
        case (_, _) => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def negate(x: ElemX): ElemX = {
      x.elem match {
        case ExInt(e1) => ElemX(ExInt(-e1))
        case ExDouble(e1) => ElemX(ExDouble(-e1))
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def fromInt(x: Int): ElemX = ElemX(x)

    def toInt(x: ElemX): Int = {
      x.elem match {
        case ExInt(e1) => e1
        case ExDouble(e2) => e2.toInt
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def toLong(x: ElemX): Long = {
      x.elem match {
        case ExInt(e1) => e1.toLong
        case ExDouble(e2) => e2.toLong
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def toFloat(x: ElemX): Float = {
      x.elem match {
        case ExInt(e1) => e1.toFloat
        case ExDouble(e2) => e2.toFloat
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def toDouble(x: ElemX): Double = {
      x.elem match {
        case ExInt(e1) => e1.toDouble
        case ExDouble(e2) => e2.toDouble
        case _ => throw new Exception(FrameErrorMessages.ILLEGAL_OPERATE_TYPE)
      }
    }

    def compare(x: ElemX, y: ElemX): Int = {

      (x.elem, y.elem) match {
        case (ExInt(e1), ExInt(e2)) => {
          if (e1 >= e2) {
            1
          } else {
            -1
          }
        }
        case (ExDouble(e1), ExDouble(e2)) => {
          if (e1 >= e2) {
            1
          } else {
            -1
          }
        }
        case (_, _) => throw new Exception("")
      }
    }

  }

  implicit object NumericForElemX extends NumericForElemX

}