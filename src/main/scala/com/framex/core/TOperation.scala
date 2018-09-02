/*
 * Copyright (c) 2018.
 */

package com.framex.core


sealed trait UnaryTOperation[A, B]
case class IntOp(x: Int) extends UnaryTOperation[Int, Int]
case class DoubleOp(x: Double) extends UnaryTOperation[Double, Double]
case class StringOp(x: String) extends UnaryTOperation[String, String]
