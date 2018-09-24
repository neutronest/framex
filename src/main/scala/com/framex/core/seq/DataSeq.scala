/*
 * Copyright (c) 2018.
 */

package com.framex.core.seq

import com.framex.core.elem.DataElem


case class DataSeq[A](title: String, dataVec: Vector[DataElem[A]]) extends FSeq[A] {

}
