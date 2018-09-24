/*
 * Copyright (c) 2018.
 */

package com.framex.core.elem

case class GroupByElem[A](key: Vector[FElem[_]], data:DataElem[A]) extends FElem[A] {

}

