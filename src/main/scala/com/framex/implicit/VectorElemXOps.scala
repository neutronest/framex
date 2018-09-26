/*
 * Copyright (c) 2018.
 */

package com.framex.`implicit`

import com.framex.core.ElemX
import com.framex.core.archive.Expr.{ExDouble, ExInt}


object VectorElemXOps {

  implicit class VectorElemXOps(self: Vector[ElemX]) {
    val n = implicitly[Numeric[ElemX]]
    import n._

    def median(): ElemX = {
      val (lower, upper) = self.sortWith(_<_).splitAt(self.size / 2)
      if (self.size % 2 == 0) (lower.last + upper.head) * ElemX(ExDouble(0.5)) else upper.head
    }

    def average(): ElemX = self.sum / ElemX(ExDouble(self.length.toDouble))
  }

}
