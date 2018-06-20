/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core.ElemX
import com.framex.core.Expr.{ ExInt}
import org.scalatest.{FlatSpec, Matchers}

class TestElemX extends FlatSpec with Matchers {


  val n = implicitly[Numeric[ElemX]]
  import n._

  it should "type ElemX should can Ord and Eq" in {

    val e1 = ElemX(ExInt(1))
    val e2 = ElemX(ExInt(1))
    e1.equals(e2) shouldEqual(true)

  }

  it should "ElemX Int basic math operation" in {

    val e1 = ElemX(ExInt(2))
    val e2 = ElemX(ExInt(3))
    val e3 = e1 + e2
    val e4 = e1 - e2
    val e5 = e1 * e2

    e3.equals(ElemX(ExInt(5))) shouldEqual(true)
    e4.equals(ElemX(ExInt(-1))) shouldEqual(true)
    e5.equals(ElemX(ExInt(6))) shouldEqual(true)

  }

  it should "Vector[ElemX] get sum" in {
    val vec = Vector[ElemX](
      ElemX(ExInt(1)),
      ElemX(ExInt(2)),
      ElemX(ExInt(3))
    )
    vec.sum.equals(ElemX(ExInt(6))) shouldEqual(true)
  }

  it should "Vector[ElemX] get max / min " in {
    val vec = Vector[ElemX](
      ElemX(ExInt(1)),
      ElemX(ExInt(2)),
      ElemX(ExInt(3))
    )
    vec.max.equals(ElemX(ExInt(3))) shouldEqual(true)
    vec.min.equals(ElemX(ExInt(1))) shouldEqual(true)
  }
}
