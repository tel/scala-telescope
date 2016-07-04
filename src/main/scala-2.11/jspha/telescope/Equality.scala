package jspha.telescope

import jspha.telescope.deps.Teq

import scala.language.higherKinds
import jspha.telescope.impl.Optic

trait Equality[S, T, A, B]
  extends Optic[Equality.AnyP, S, T, A, B]
    with Iso[S, T, A, B] { outer =>

  def eq1: S Teq A =
    new Teq[A, S] {
      def subst[F[_]](fs: F[A]): F[S] =
        outer[Lambda[(X, Y) => F[X]]](fs)(Equality.anyP)
    }.from

  def eq2: B Teq T =
    new Teq[B, T] {
      def subst[F[_]](fb: F[B]): F[T] =
        outer[Lambda[(X, Y) => F[Y]]](fb)(Equality.anyP)
    }

}

object Equality {

  /**
    * The superclass of all Profunctor constraints.
    */
  type AnyP[~>[_, _]] = Any

  /**
    * The trivial inhabitant of AnyP
    */
  def anyP[~>[_, _]]: AnyP[~>] = Unit

  def apply[S, T, A, B](eq1f: S Teq A, eq2f: B Teq T) =
    new Equality[S, T, A, B] {

      def apply[~>[_, _]](p: A ~> B)(implicit ev: AnyP[~>]) =
        eq2f.subst[S ~> ?](eq1f.from.subst[? ~> B](p))

      override def eq1 = eq1f

      override def eq2 = eq2f

    }

  implicit class OfOptic[S, T, A, B](t: Optic[AnyP, S, T, A, B]) extends Equality[S, T, A, B] {
    def apply[~>[_, _]](p: A ~> B)(implicit ev: AnyP[~>]) =
      t.apply(p)(ev)
  }

}
