package jspha.telescope.deps

import scala.language.higherKinds
import cats.Traverse

trait Wander[~>[_,_]] extends Visit[~>] {
  def wander[F[_] : Traverse, A, B](p: A ~> B): F[A] ~> F[B]
}
object Wander {
  def apply[~>[_, _]](implicit ev: Wander[~>]): Wander[~>] = ev
}
