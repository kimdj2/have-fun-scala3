package chapter3

import scala.util.Try

object PostConditionExample:
  opaque type WrappedResult[T] = T
  def result[T](using r: WrappedResult[T]): T = r
  extension [T](x: T)
    def ensuring(condition: WrappedResult[T] ?=> Boolean): T =
      assert(condition(using x))
      x

  @main def runPostCondition(): Unit =
    /** Should be 6 */
    println(Try(List(1, 2, 3).sum.ensuring(result == 6)))
    /** Should be error */
    println(Try(List(1, 2).sum.ensuring(result == 6)))
