/*
 * Copyright (c) 2018.
 */
package com.framex.core

import com.framex.core.Expr.BottomType
import scalaz.{:+:, Applicative, Functor, Monad, Coyoneda, Yoneda}
import shapeless.HList



trait Elem[A]
case class ElemData[A](x: A) extends Elem[A]
case class ElemMap[A, B](func: A => B, elem: Elem[A]) extends Elem[B]
case class ElemGroup[A, B]()

object Elem {

  implicit val elemFunctor : Functor[Elem] = new Functor[Elem] {
    override def map[A, B](fa: Elem[A])(f: A => B): Elem[B] = {
      val e = ElemMap(f, fa)
      fa match {
        case ElemData(x: A) => ElemData(e.func.apply(x))
        case _ => throw new Exception("呵呵吼")
      }
    }
  }

}


trait ElemSeq[A]
case class ElemSeqData[A](name: String, data: Vector[A]) extends ElemSeq[A]
case class ElemSeqMap[A, B](func: A => B, elem: ElemSeq[A]) extends ElemSeq[B]
case class ElemGroupBy[A](map: Map[HList, ElemSeq[A]]) extends ElemSeq[A]


object ElemSeq {

  implicit val elemSeqFunctor : Functor[ElemSeq] = new Functor[ElemSeq] {
    override def map[A, B](fa: ElemSeq[A])(f: A => B): ElemSeq[B] = {

      val seq = ElemSeqMap(f, fa)
      fa match {
        case ElemSeqData(name, data) => ElemSeqData(name, data.map(x => f(x)))
        case _ => throw new Exception("阿西吧")
      }

    }
  }

  implicit val elemSepApplicative : Applicative[ElemSeq] = new Applicative[ElemSeq] {
    override def point[A](a: => A): ElemSeq[A] = ???

    override def ap[A, B](fa: => ElemSeq[A])(f: => ElemSeq[A => B]): ElemSeq[B] = ???
  }

//  implicit val elemSeqMonad : Monad[ElemSeq] = new Monad[ElemSeq] {
//    override def bind[A, B](fa: ElemSeq[A])(f: A => ElemSeq[B]): ElemSeq[B] = {
//
//      fa match {
//        case ElemSeqData(name, vec: Vector[A]) => {
//          ElemSeqData(name, vec.map(f).)
//        }
//        case _ => throw new Exception("")
//      }
//
//    }
//    override def point[A](a: => A): ElemSeq[A] = ElemSeqData("", Vector())
//  }
}

trait Frame
case class FrameData(data: Vector[ElemSeq[_]]) extends Frame {}
case class FrameGroupBy(data: Map[HList, Vector[ElemSeq[_]]]) extends Frame
case class FrameMap[A, B](func: A => B, data: Frame) extends Frame


object Frame {

  import ElemSeq.elemSeqFunctor
  import scalaz.Scalaz._
  import scalaz.Functor

  val SeqFuntor = ElemSeq.elemSeqFunctor


  def applyMap[A, B](fa: Frame)(f: A => B) : Frame = {



    fa match {
      case FrameData(data) => {

        var ll = new collection.mutable.ListBuffer[ElemSeq[_]]()
        data.foreach(seq => {
          seq match {
            case ElemSeqData(name, seqData: Vector[A]) => {
              val r = SeqFuntor.map(ElemSeqData(name, seqData: Vector[A]))(f)
              ll += r
            }
            case _ => throw new Exception("")
          }
        })
        FrameData(ll.toVector)
      }
      case _ => throw new Exception("")
    }
  }
}
