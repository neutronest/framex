package com.framex



import com.framex.core.SeqX
import org.scalatest._


class TestSeqX extends FlatSpec with Matchers {

  "FromList" should "init seqx from list" in {

    val l = List(1,2,3,4,5)
    var seqX = new SeqX[Int]()
    seqX.fromList(l)
    println(seqX.length)
  }
}
