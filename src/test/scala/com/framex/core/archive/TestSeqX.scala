/*
 * Copyright (c) 2018.
 */

package com.framex.core.archive

import org.scalatest._


class TestSeqX extends FlatSpec with Matchers {

  "FromList" should "init seqx from list" in {

    val l = List(1,2,3,4,5)
    var seqX = SeqX(l)
    seqX.length shouldEqual(5)
    seqX.ndim shouldEqual(1)
    seqX.shape shouldEqual((1, 5))


  }

  it should "be able to slice" in {
    val s : SeqX = SeqX(Vector(1,2,3,4,5))
    s.length should be(5)
    s(2, 4) should be(SeqX(Vector(3,4)))
    s(1, 3) should not be(SeqX(Vector(3, 4)))
  }
}
