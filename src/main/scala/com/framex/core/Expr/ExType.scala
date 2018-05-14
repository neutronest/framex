package com.framex.core.Expr

sealed trait ExType[A]

case object Nan extends ExType[Nothing]
case class ExInt(i: Int) extends ExType[Int] {
  override def toString: String = "ExInt"
}
case class ExDouble(d: Double) extends ExType[Double] {

  override def toString: String = "ExDouble"
}
case class ExString(s: String) extends ExType[String] {

  override def toString: String = "ExString"
}
case class ExCategory(cat: List[String]) extends ExType[Enumeration] {
  override def toString: String = "ExCategory"
}



