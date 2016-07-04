package jspha.telescope.deps

import scala.language.higherKinds
import cats.functor.Strong

trait Visit[~>[_,_]] extends Strong[~>] with Choice[~>]

object Visit {
  def apply[~>[_, _]](implicit ev: Visit[~>]): Visit[~>] = ev
}
