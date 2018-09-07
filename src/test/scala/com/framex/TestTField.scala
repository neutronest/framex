/*
 * Copyright (c) 2018.
 */

package com.framex

import com.framex.core.{IntField, TField}
import com.framex.core.TField.CoyoTField
import org.scalatest.{FlatSpec, Matchers}
import scalaz.Coyoneda

class TestTField extends FlatSpec with Matchers {


  it should "test coyoneda" in {

    import scalaz._

    val fn = (x :Int) => x + 2
    val t1 : Coyoneda[TField, Int] = Coyoneda.lift(IntField(2))
    print(t1.fi)
    val t2 = t1.map(fn)
    print(t2.fi)
    print("foobar")


//
//    val ct2 = TField.fmap(n)(fn)
//    print("axibabababab")
//    print(ct2.unlift)
  }
}
