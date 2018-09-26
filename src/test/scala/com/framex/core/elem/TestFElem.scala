/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core.elem.DataElem
import org.scalatest.{FlatSpec, Matchers}

class TestFElem extends FlatSpec with Matchers{

  it should "add operation" in {

    val e1 = DataElem(1)
    val e2 = DataElem(2)
    val e3 = e1 + e2
    print(e3)
    print("foobar")
  }
}
