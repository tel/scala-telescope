package jspha.telescope.deps

import cats.functor.{Profunctor, Strong}

object Orphans {

  implicit object functionIsProfunctor extends functionIsProfunctor
  trait functionIsProfunctor extends Profunctor[Function1] {
    def dimap[A, B, C, D](fab: A => B)(f: C => A)(g: B => D) =
      f andThen fab andThen g
  }

  implicit object functionIsStrong extends functionIsStrong
  trait functionIsStrong extends functionIsProfunctor with Strong[Function1] {
    def first[A, B, C](fa: A => B) = { case (s, c) => (fa(s), c) }
    def second[A, B, C](fa: A => B) = { case (c, s) => (c, fa(s)) }
  }

  implicit object functionIsChoice extends functionIsChoice
  trait functionIsChoice extends functionIsProfunctor with Choice[Function1] {
    def left[A, B, C](p: A => B) = _.bimap(p, identity)
    def right[A, B, C](p: A => B) = _.bimap(identity, p)
  }

}
