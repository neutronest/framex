package com.framex.core

import com.framex.core.Expr.{ExType, Expr}

trait BaseFrame[A] {
  def data: ExType[A]
  def ndim: Int
  def shape: List[Int]
}
