package jspha.telescope.deps

import scala.language.higherKinds
import cats.Functor

/**
  * Note: Probably remove this. Not sure if it pays its way.
  */
trait Mapping[~>[_, _]] extends Wandering[~>] {
  def map[F[_] : Functor, A, B](p: A ~> B): F[A] ~> F[B]
}

object Mapping {
  def apply[~>[_, _]](implicit ev: Mapping[~>]): Mapping[~>] = ev

  implicit object functionIsMapping extends functionIsMapping
  trait functionIsMapping
    extends Mapping[Function1]
      with Wandering.functionIsWandering {
    def map[F[_] : Functor, A, B](p: A => B) =
      (fa: F[A]) => Functor[F].map(fa)(p)
  }

}

