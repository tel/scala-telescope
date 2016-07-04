package jspha.telescope.impl

import scala.language.higherKinds

/**
  * An `Optic` is a Profunctor transformer and forms the heart of "pure
  * Profunctor" lens design.
  */
trait Optic[-C[_[_, _]], S, T, A, B] {
  outer =>

  def apply[~>[_, _]](p: A ~> B)(implicit ev: C[~>]): S ~> T

  //  def andThen[X, Y](next: Optic[C, X, Y, S, T]) =
  //    new Optic[C, X, Y, A, B] {
  //      def apply[~>[_, _]](p: A ~> B)(implicit ev: C[~>]) =
  //        next(outer(p))
  //    }
}

object Optic {

  trait Ob[C[_[_, _]], L[_, _, _, _]] {

//    implicit class OfOptic[S, T, A, B](t: Optic[C, S, T, A, B]) extends L[S, T, A, B] {
//      def apply[~>[_, _]](p: A ~> B)(implicit ev: C[~>]) =
//        t.apply(p)(ev)
//    }

  }

}
