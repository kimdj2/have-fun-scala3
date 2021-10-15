package chapter3

import scala.concurrent.{ ExecutionContext, Future }

import MonoidInstances.given

object UsingExample:

  def max[T](x: T, y: T)(using ord: Ordering[T]) =
    if ord.compare(x, y) < 0 then y else x

  @main def runMaxExample: Unit =
    assert(max(1, 2) == 2)
    assert(max("abcdefg", "hijklmn") == "hijklmn")

  def sum[T](list: List[T])(using ev: Monoid[T]) =
    list.foldLeft(ev.unit)(ev.add)

  @main def runSumExample: Unit =
    assert(sum(List(1, 2, 3, 4, 5)) == 15)
    assert(sum(List("Hello", ",", " ", "world", "!")) == "Hello, world!")
    assert(sum(List(List(1, 2, 3), List(4, 5))) == List(1, 2, 3, 4, 5))
    assert(sum(List(List("Hello", ",", " "), List("world", "!"))) == List("Hello", ",", " ", "world", "!"))
    assert(sum(sum(List(List("Hello", ",", " "), List("world", "!")))) == "Hello, world!")
    assert(sum(List(Option(1), Option(2), None, Option(3), Option(4), Option(5))) == Some(15))

  @main def runWithImport: Unit =
    import scala.concurrent.ExecutionContext.Implicits.global
    usingExecutionContext

  // Alias given
  @main def runWithAliasGiven: Unit =
    given global: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
    usingExecutionContext

  // Alias anounymous given
  @main def runWithAliasAnounymousGiven: Unit =
    given ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
    usingExecutionContext

  def usingExecutionContext(using ExecutionContext) =
    Future {
      (1 to 10).foreach { i =>
        Thread.sleep(1000)
        println(i)
      }
    }
