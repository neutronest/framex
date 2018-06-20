/*
 * Copyright (c) 2018.
 */

package com.framex.stats

import com.framex.core.{ElemX, FrameX}


object Stats {

  type VectorElemX = Vector[ElemX]

  implicit class FrameStats(var df: FrameX) {

//    def sum() : FrameX = {
//
//      val columnNames = df.columnNames
//      df.data.map(vec => vec.sum)
//    }
  }
}

