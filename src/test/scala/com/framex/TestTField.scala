/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core._
import org.scalatest.{FlatSpec, Matchers}

class TestTField extends FlatSpec with Matchers {


  it should "test applyMap" in {


    import Elem.elemFunctor
    import scalaz.Scalaz._



    val fn = (x: Int) => x * 2
    val e1 : Elem[Int] = ElemData(100)
    val e2 = e1 map fn
    e2 match {
      case ElemData(x) => print(x)
      case _ => print("axiba")
    }
    print("hhhh")

    val elemSeq1 = ElemSeqData("title", Vector(1,2,3,4,5))
    val ff1 = FrameData(Vector(elemSeq1))

    val ff2 = Frame.applyMap(ff1)(fn)
    print(ff2)





  }
  //  it should "test coyoneda" in {
  //
  //    type ISB = Int :+: Double :+: String :+: CNil
  //
  //    object size extends Poly1 {
  //      implicit def caseInt = at[Int](i => (i, i))
  //      implicit def caseString = at[String](s => (s, s.length))
  //      implicit def caseBoolean = at[Boolean](b => (b, 1))
  //    }
  //
  //    object reverse2 extends Poly1 {
  //      implicit def caseString =  at[String](x => x.reverse)
  //    }
  //
  //    val isb = Coproduct[ISB]("foo")
  //    val isbr = isb map reverse2
  //    print(isbr.select[String])
  //
  //    print("123")
  //
  //
  //
  //  }
}
