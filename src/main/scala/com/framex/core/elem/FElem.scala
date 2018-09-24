/*
 * Copyright (c) 2018.
 */

package com.framex.core.elem

trait FElem[+A]

case class UnaryFnElem[A, B](f: A => B) extends FElem[B]
case class MapContextElem[A, B]( fn: A => B, data: A) extends FElem[B]
