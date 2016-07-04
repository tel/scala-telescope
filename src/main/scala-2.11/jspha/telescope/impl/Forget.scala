package jspha.telescope.impl

import cats.Monoid

import scala.language.implicitConversions
import cats.functor.{Profunctor, Strong}
import jspha.telescope.deps.Choice

class Forget[S] {

  case class ~>[A, B](f: A => S) extends Function[A, S] {
    def apply(a: A): S = f(a)
  }

  def ignore[A]: S ~> A = ~>(identity)

  implicit object isProfunctor extends isProfunctor

  trait isProfunctor extends Profunctor[~>] {
    def dimap[A, B, C, D](fab: A ~> B)(f: C => A)(g: B => D) =
      ~>(f andThen fab)
  }

  object isStrong extends isProfunctor with Strong[~>] {
    def first[A, B, C](fa: A ~> B) = ~> { case (s, _) => fa(s) }

    def second[A, B, C](fa: A ~> B) = ~> { case (_, s) => fa(s) }
  }

  implicit class isChoice(M: Monoid[S]) extends isProfunctor with Choice[~>] {
    def left[A, B, C](p: ~>[A, B]) = ~>(_.fold(p, _ => M.empty))

    def right[A, B, C](p: ~>[A, B]) = ~>(_.fold(_ => M.empty, p))
  }

}

