package com.framex.core.Expr

sealed trait ExType[+A]

case object Nan extends ExType[Nothing]
case class ExInt(i: Int) extends ExType[Int]
case class ExDouble(d: Double) extends ExType[Double]
case class ExString(s: String) extends ExType[String]
case class ExCategory(cat: List[String]) extends ExType[Enumeration]



