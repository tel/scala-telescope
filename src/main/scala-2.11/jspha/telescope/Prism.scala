package jspha.telescope

import scala.language.higherKinds
import jspha.telescope.deps.Choice
import jspha.telescope.impl.Optic
import cats.data.Xor

trait Prism[S, T, A, B]
  extends Optic[Choice, S, T, A, B] {

  def build(b: B): T = ???

  def select(s: S): A Xor T = ???

}

object Prism {

  def apply[S, T, A, B](buildf: B => T, selectf: S => A Xor T) =
    new Prism[S, T, A, B] {
      def apply[~>[_, _]](p: A ~> B)(implicit ev: Choice[~>]) =
        ev.dimap[A Xor T, B Xor T, S, T](ev.left(p))(selectf) {
          (x: B Xor T) => x.fold(buildf, identity)
        }

      override def select(s: S) = selectf(s)

      override def build(b: B) = buildf(b)
    }

  implicit class OfOptic[S, T, A, B](t: Optic[Choice, S, T, A, B]) extends Prism[S, T, A, B] {
    def apply[~>[_, _]](p: A ~> B)(implicit ev: Choice[~>]) =
      t.apply(p)(ev)
  }

}
