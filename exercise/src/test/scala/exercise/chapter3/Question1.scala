package exercise.chapter3

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import Question1.given
import scala.util.NotGiven

class Question1Spec extends AnyFlatSpec with Matchers {

  class WrappedOrd[T](ord: Ord[T]):
    def compare(x: T, y: T): Int = ord.compare(x, y)
  object WrappedOrd:
    given [T](using ord: Ord[T]): WrappedOrd[T] = WrappedOrd(ord)
    given [T](using NotGiven[Ord[T]]): WrappedOrd[T] = WrappedOrd(
      new Ord[T]:
        def compare(x: T, y: T): Int = 0
    )

  "Ord[Int] インスタンス" should "1 < 4" in {
    assert(summon[WrappedOrd[Int]].compare(1, 4) == -1)
  }

  "Ord[Int] インスタンス" should "5 > 4" in {
    assert(summon[WrappedOrd[Int]].compare(5, 4) == 1)
  }

  "Ord[Int] インスタンス" should "3 = 3" in {
    assert(summon[WrappedOrd[Int]].compare(3, 3) == 0)
  }

  "Ord[Option[Int]] インスタンス" should "None = None" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(None, None) == 0)
  }

  "Ord[Option[Int]] インスタンス" should "None < Some(_)" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(None, Some(5)) == -1)
  }

  "Ord[Option[Int]] インスタンス" should "Some(_) < None" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(Some(1), None) == 1)
  }

  "Ord[Option[Int]] インスタンス" should "Some(3) < Some(5)" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(Some(3), Some(5)) == -1)
  }

  "Ord[Option[Int]] インスタンス" should "Some(3) = Some(3)" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(Some(3), Some(3)) == 0)
  }

  "Ord[Option[Int]] インスタンス" should "Some(5) > Some(3)" in {
    assert(summon[WrappedOrd[Option[Int]]].compare(Some(5), Some(3)) == 1)
  }
}
