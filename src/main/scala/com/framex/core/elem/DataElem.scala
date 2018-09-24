/*
 * Copyright (c) 2018.
 */

package com.framex.core.elem

case class DataElem[A](data: A) extends FElem[A] {

  def +(other:DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.plus(this.data, other.data))
  }


  def -(other: DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.minus(this.data, other.data))
  }


  def *(other: DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.times(this.data, other.data))
  }
}


object DataElem {

  def plus[A](de1: DataElem[A], de2:DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.plus(de1.data, de2.data))
  }

  def minus[A](x: DataElem[A], y: DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.minus(x.data, y.data))
  }

  def times[A](x: DataElem[A], y: DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.times(x.data, y.data))
  }

  def compare[A](x: DataElem[A], y: DataElem[A])(implicit num: Numeric[A]) : Int = {
    num.compare(x.data, y.data)
  }

  def toDouble[A](x: DataElem[A])(implicit num: Numeric[A]) : Double = {
    num.toDouble(x.data)
  }

  def toInt[A](x: DataElem[A])(implicit num: Numeric[A]) : Int = {
    num.toInt(x.data)
  }

  def toLong[A](x: DataElem[A])(implicit num: Numeric[A]) : Long = {
    num.toLong(x.data)
  }

  def toFloat[A](x: DataElem[A])(implicit num: Numeric[A]) : Float = {
    num.toFloat(x.data)
  }

  def negate[A](x: DataElem[A])(implicit num: Numeric[A]) : DataElem[A] = {
    DataElem(num.negate(x.data))
  }

  def fromInt[A](value: Int)(implicit num: Numeric[A]) : DataElem[Int] = {
    DataElem(value)
  }
}