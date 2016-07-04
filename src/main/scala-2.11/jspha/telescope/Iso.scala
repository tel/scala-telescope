package jspha.telescope

import scala.language.higherKinds
import cats.functor.Profunctor
import jspha.telescope.impl.Optic

trait Iso[S, T, A, B]
  extends Optic[Profunctor, S, T, A, B]
    with Lens[S, T, A, B]
    with Prism[S, T, A, B] {

  def to(s: S): A =
    ???

  def from(b: B): T =
    ???

}

object Iso {

  def apply[S, T, A, B](tof: S => A, fromf: B => T) =
    new Iso[S, T, A, B] {

      def apply[~>[_, _]](p: A ~> B)(implicit ev: Profunctor[~>]) =
        ev.dimap(p)(tof)(fromf)

      override def to(s: S) = tof(s)

      override def from(b: B) = fromf(b)

    }

  implicit class OfOptic[S, T, A, B](t: Optic[Profunctor, S, T, A, B]) extends Iso[S, T, A, B] {
    def apply[~>[_, _]](p: A ~> B)(implicit ev: Profunctor[~>]) =
      t.apply(p)(ev)
  }

}
