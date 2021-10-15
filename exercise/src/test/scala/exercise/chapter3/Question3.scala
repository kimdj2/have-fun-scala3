package exercise.chapter3

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import Question1.given
import Question3.*

class Question3Spec extends AnyFlatSpec with Matchers {

  extension [T](x: T)
    def some: Option[T] =
      Option(x)

  def none[T]: Option[T] = None

  "Option[Int] > Option[Int]" should "Ord[Option[Int]] にしたがって、数を比較できる" in {
    assert(3.some  > 2.some      == true)
    assert(0.some  > 0.some      == false)
    assert(1.some  > 3.some      == false)
    assert(None    > Some(3)     == false)
    assert(Some(3) > None        == true)
    assert(none[Int] > none[Int] == false)
  }

  "Option[Int] < Option[Int]" should "Ord[Option[Int]] にしたがって、数を比較できる" in {
    assert(3.some  < 2.some      == false)
    assert(0.some  < 0.some      == false)
    assert(1.some  < 3.some      == true)
    assert(None    < Some(3)     == true)
    assert(Some(3) < None        == false)
    assert(none[Int] < none[Int] == false)
  }

  "List[Int].max" should "Ord[Int] にしたがって、最大の数を返す" in {
    assert(List(1, 2, 5, 4, 3).max == 5)
    assert(List(3)            .max == 3)
    assert(List(4, 4)         .max == 4)
  }

  "List[Option[Int]].max" should "Ord[Option[Int]] にしたがって、最大の数を返す" in {
    assert(List(Some(1), None)                  .max == Some(1))
    assert(List(none[Int], none[Int])           .max == None)
    assert(List(Some(5), None, Some(4), Some(1)).max == Some(5))
  }
}
