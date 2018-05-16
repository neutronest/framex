package com.framex.core.Expr

sealed trait ExType

case object Nan extends ExType {
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case Nan => true
      case _ => false
    }
  }
}
case class ExInt(x: Int) extends ExType {
//  override def equals(obj: Any): Boolean = {
//    obj match {
//      case ExInt(y) => x == y
//      case _ => false
//    }
//  }
}
case class ExDouble(x: Double) extends ExType {
//  override def equals(obj: Any): Boolean = {
//    obj match {
//      case ExDouble(y) => math.abs(x - y) < 0.000000001
//      case _ => false
//    }
//  }
}
case class ExString(x: String) extends ExType {
//  override def equals(obj: Any): Boolean = {
//    obj match {
//      case ExString(y) => x.equals(y)
//      case _ => false
//    }
//  }
}
case class ExCategory(x: List[String]) extends ExType {
  override def equals(obj: Any): Boolean = ???
}
case class ExOther(x: Any) extends ExType

object ExType {

  def apply[A: BottomType](data: A): ExType = {
    data match {
      case x: Int => ExInt(x)
      case x: Double => ExDouble (x)
      case x: String => ExString (x)
      case _ => Nan
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



