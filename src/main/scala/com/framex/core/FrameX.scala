/*
 * Copyright (c) 2018.
 */

package com.framex.core

import com.framex.core.seq.FSeq

class FrameX(val data: Vector[FSeq[_]]) {

  def columnNames(): String = ???

  def head(n: Int = 5) : FrameX = ???

  def tail(n: Int = 5) : FrameX = ???

  def loc() : FrameX = ???

}

