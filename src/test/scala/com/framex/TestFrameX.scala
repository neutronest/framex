/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core.FrameX
import com.framex.core.elem.DataElem
import com.framex.core.seq.DataSeq
import org.scalatest.{FlatSpec, Matchers}

class TestFrameX extends FlatSpec with Matchers {


  def loadNumericFramex() : FrameX = {

    FrameX(
      Vector(
        DataSeq(
          "id",
          Vector(
            DataElem(1),
            DataElem(2),
            DataElem(3)
          )
        ),

        DataSeq(
          "score",
          Vector(
            DataElem(10),
            DataElem(20),
            DataElem(30)
          )
        )
      )
    )

  }

  it should "test applyMap" in {

    val fx = loadNumericFramex()
    val fx2 = fx.applyMap( (x: Int) => x*2)
    fx2 shouldEqual FrameX(
      Vector(
        DataSeq(
          "id",
          Vector(
            DataElem(2),
            DataElem(4),
            DataElem(6)
          )
        ),

        DataSeq(
          "score",
          Vector(
            DataElem(20),
            DataElem(40),
            DataElem(60)
          )
        )
      )
    )
  }

}
