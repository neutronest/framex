package com.framex.`implicit`

object IndexOps {

  implicit class IntOps(private val self: Int) extends AnyVal {
    def ::(that: Int): Range = Range.inclusive(that, self)
  }

}
