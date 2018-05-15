package com.framex



import com.framex.core.SeqX
import org.scalatest._


class TestSeqX extends FlatSpec with Matchers {

  "FromList" should "init seqx from list" in {

    val l = List(1,2,3,4,5)
    var seqX = new SeqX[Int]()
    seqX.fromList(l)
    for (item <- seqX) {
      println(item.data.toString)
    }
    seqX.length shouldEqual(5)
    seqX.ndim shouldEqual(1)
    seqX.shape shouldEqual((1, 5))


  }
}
