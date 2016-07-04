package jspha.telescope.impl

import scala.language.higherKinds

/**
  * An `Optic` is a Profunctor transformer and forms the heart of "pure
  * Profunctor" lens design.
  */
trait Optic[-C[_[_, _]], S, T, A, B] {

  def apply[~>[_, _]](p: A ~> B)(implicit ev: C[~>]): S ~> T

}
