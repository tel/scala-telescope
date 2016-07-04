package jspha.telescope.deps

import scala.language.higherKinds
import cats.{Applicative, Id, Traverse}
import cats.functor.Strong
import jspha.telescope.impl.ReprOptic
import jspha.telescope.orphans.{functionIsChoice, functionIsStrong}

trait Wandering[~>[_, _]] extends Strong[~>] with Choice[~>] {
  def map[F[_] : Traverse, A, B](p: A ~> B): F[A] ~> F[B] =
    wander(ReprOptic.traverse[F, A, B])(p)

  def wander[S, T, A, B](rep: ReprOptic[Applicative, S, T, A, B])(p: A ~> B): S ~> T
}

object Wandering {
  def apply[~>[_, _]](implicit ev: Wandering[~>]): Wandering[~>] = ev

  implicit object functionIsWandering extends functionIsWandering
  trait functionIsWandering
    extends Wandering[Function1]
      with functionIsStrong
      with functionIsChoice {
    def wander[S, T, A, B](rep: ReprOptic[Applicative, S, T, A, B])(p: A => B) =
      rep.apply[Id](p)

    override def map[F[_] : Traverse, A, B](p: A => B) =
      (fa: F[A]) => Traverse[F].map(fa)(p)
  }
}
