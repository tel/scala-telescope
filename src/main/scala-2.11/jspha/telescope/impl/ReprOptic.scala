package jspha.telescope.impl

import cats.{Applicative, Traverse}

import scala.language.higherKinds

/**
  * `ReprOptic[C]` is similar to `Optic[C]` except that instead of
  * constraining the profunctor directly we weaken the profunctor to a
  * representable profunctor (one equivalent to `A => F[B]` for some `F`) and
  * then constrain the `F`. Importantly, this is the shape of `traverse`.
  */
trait ReprOptic[-C[_[_]], S, T, A, B] {

  def apply[F[_]](p: A => F[B])(implicit ev: C[F]): S => F[T]

}

object ReprOptic {

  type AnyC[F[_]] = Any

  def traverse[G[_] : Traverse, A, B] =
    new ReprOptic[Applicative, G[A], G[B], A, B] {
      def apply[F[_]](p: A => F[B])(implicit ev: Applicative[F]): G[A] => F[G[B]] =
        (ga: G[A]) => Traverse[G].traverse[F, A, B](ga)(p)
    }

  def id[A, B] =
    new ReprOptic[AnyC, A, B, A, B] {
      def apply[F[_]](p: A => F[B])(implicit ev: AnyC[F]) = p
    }

}