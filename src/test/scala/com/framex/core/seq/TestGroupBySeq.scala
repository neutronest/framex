/*
 * Copyright (c) 2018.
 */

package com.framex.core.seq

import com.framex.core.elem.{DataElem, GroupByElem}
import org.scalatest.{FlatSpec, Matchers}

class TestGroupBySeq extends FlatSpec with Matchers {

  def loadGroupByData() : GroupBySeq[Double] = {

    val data : Vector[GroupByElem[Double]] = Vector(
      GroupByElem(Vector(DataElem(1), DataElem("a")), DataElem(12)),
      GroupByElem(Vector(DataElem(1), DataElem("a")), DataElem(13)),
      GroupByElem(Vector(DataElem(1), DataElem("a")), DataElem(14)),
      GroupByElem(Vector(DataElem(2), DataElem("b")), DataElem(15)),
      GroupByElem(Vector(DataElem(2), DataElem("b")), DataElem(16)),
      GroupByElem(Vector(DataElem(2), DataElem("b")), DataElem(17)),
      GroupByElem(Vector(DataElem(2), DataElem("b")), DataElem(18)),
      GroupByElem(Vector(DataElem(3), DataElem("c")), DataElem(19))
    )
    GroupBySeq("test", data)
  }

  it should "Test map method" in {

    val groupBySeq = loadGroupByData()
    val groupBySeqMap = groupBySeq.map()
    print(groupBySeqMap)
    print("foobar")
    groupBySeqMap shouldEqual Map(
      Vector(DataElem(1), DataElem("a")) -> DataSeq("test", Vector(DataElem(12.0), DataElem(13.0), DataElem(14.0))),
      Vector(DataElem(2), DataElem("b")) -> DataSeq("test", Vector(DataElem(15.0), DataElem(16.0), DataElem(17.0), DataElem(18.0))),
      Vector(DataElem(3), DataElem("c")) -> DataSeq("test", Vector(DataElem(19.0)))
    )
  }
}
