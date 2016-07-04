package jspha.telescope.deps

import cats.data.Xor

import scala.language.higherKinds
import cats.functor.Profunctor

trait Choice[~>[_, _]] extends Profunctor[~>] {
  def left[A, B, C](p: A ~> B): (A Xor C) ~> (B Xor C)
  def right[A, B, C](p: A ~> B): (C Xor A) ~> (C Xor B)
}

object Choice {
  def apply[~>[_, _]](implicit ev: Choice[~>]): Choice[~>] = ev
}
