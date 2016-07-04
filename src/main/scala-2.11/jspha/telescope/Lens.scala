package jspha.telescope

import scala.language.higherKinds
import cats.functor.Strong
import jspha.telescope.impl.{Forget, Optic}

trait Lens[S, T, A, B]
  extends Optic[Strong, S, T, A, B] {

  // Only necessary when `get` is used.
  private lazy val F =
    new Forget[A]

  def get(s: S) =
    apply(F.ignore[B])(F.isStrong)(s)

  def over(f: A => B)(s: S) =
    apply(f)(orphans.functionIsStrong)(s)

  def put(b: B, s: S) =
    over(_ => b)(s)

}

object Lens {

  def apply[S, T, A, B](getf: S => A, putf: (B, S) => T) =
    new Lens[S, T, A, B] {
      def apply[~>[_, _]](p: ~>[A, B])(implicit ev: Strong[~>]) =
        ev.dimap[(A, S), (B, S), S, T](ev.first(p)) {
          (s: S) => (getf(s), s)
        } {
          (x: (B, S)) => putf(x._1, x._2)
        }

      override def get(s: S): A = getf(s)

      override def put(b: B, s: S) = putf(b, s)

      override def over(f: A => B)(s: S) = put(f(get(s)), s)
    }

  implicit class OfOptic[S, T, A, B](t: Optic[Strong, S, T, A, B]) extends Lens[S, T, A, B] {
    def apply[~>[_, _]](p: A ~> B)(implicit ev: Strong[~>]) =
      t.apply(p)(ev)
  }

}


