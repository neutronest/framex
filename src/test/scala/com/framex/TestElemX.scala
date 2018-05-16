package com.framex

import com.framex.core.ElemX
import com.framex.core.Expr.{ExInt, ExType}
import org.scalatest.{FlatSpec, Matchers}

class TestElemX extends FlatSpec with Matchers {

  it should "type ElemX should can Ord and Eq" in {

    val e1 = ElemX(ExInt(1))
    val e2 = ElemX(ExInt(1))
    e1.equals(e2) shouldEqual(true)

  }
}
