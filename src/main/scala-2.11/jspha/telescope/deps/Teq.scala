package jspha.telescope.deps

import scala.language.higherKinds
import scala.language.implicitConversions

/**
  * `Teq[A, B]` is a witness that the types `A` and `B` are equal. It is more
  * powerful than the standard `A =:= B` since it offers the ability to
  * convert types appearing within other structures.
  */
abstract class Teq[A, B] {
  def subst[F[_]](fa: F[A]): F[B]

  final def apply(a: A): B =
    Teq.witness(this)(a)

  final def andThen[C](next: B Teq C): A Teq C =
    next.subst[A Teq ?](this)

  final def compose[C](prev: C Teq A): C Teq B =
    prev andThen this

  final def from: B Teq A =
    this.subst[? Teq A](Teq.refl)

  final def lift[F[_]]: F[A] Teq F[B] =
    subst[Lambda[X => F[A] Teq F[X]]](Teq.refl)
}

object Teq {

  /**
    * The only real value of `Teq` is the statement `A Teq A`.
    */
  implicit def refl[A]: A Teq A = new Teq[A, A] {
    def subst[F[_]](fa: F[A]): F[A] = fa
  }

  /**
    * A `Teq` immediately furnishes a coercion function.
    */
  implicit def witness[A, B](t: A Teq B): A => B =
    t.subst[A => ?](identity)

  /**
    * We can convert `A Teq B` to a `A =:= B` witness via substitution.
    */
  implicit def scalaEq[A, B](t: A Teq B): A =:= B =
    t.subst[A =:= ?](implicitly[A =:= A])

}
