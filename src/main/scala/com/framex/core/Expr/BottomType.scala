
/*
 * Copyright (c) 2018.
 */

package com.framex.core.Expr

class BottomType[T]

object BottomType {

  implicit object BottomForNothing extends BottomType[Nothing]

  implicit object BottomForInt extends BottomType[Int]

  implicit object BottomForDouble extends BottomType[Double]

  implicit object BottomForChar extends BottomType[Char]

  implicit object BottomForString extends BottomType[String]

  implicit object BottomForEnum extends BottomType[Enumeration]
}
