package jspha.telescope

import cats.Applicative

import scala.language.higherKinds
import jspha.telescope.deps.Wandering
import jspha.telescope.impl.{Optic, ReprOptic}

trait Traversal[S, T, A, B]
  extends Optic[Wandering, S, T, A, B] {

  def traverse[F[_]](p: A => F[B]): S => F[T] = ???
}

object Traversal {

  def apply[S, T, A, B](rep: ReprOptic[Applicative, S, T, A, B]) =
    new Traversal[S, T, A, B] {
      def apply[~>[_, _]](p: ~>[A, B])(implicit ev: Wandering[~>]): S ~> T =
        ev.wander(rep)(p)

      override def traverse[F[_]](p: A => F[B]) = rep(p)
    }

  implicit class OfOptic[S, T, A, B](t: Optic[Wandering, S, T, A, B]) extends Traversal[S, T, A, B] {
    def apply[~>[_, _]](p: A ~> B)(implicit ev: Wandering[~>]) =
      t.apply(p)(ev)
  }

}

