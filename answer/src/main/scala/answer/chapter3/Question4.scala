package answer.chapter3

import scala.concurrent.{ Future, ExecutionContext }
import scala.language.implicitConversions

trait Functor[F[_]]:
  def fmap[A, B](f: A => B): F[A] => F[B]
  extension [A](lhs: F[A])
    def map[B](f: A => B): F[B] = fmap(f)(lhs)

/** Question: Functor instances */
object Question4:

  given Functor[Option] with
    def fmap[A, B](f: A => B): Option[A] => Option[B] = _.map(f)

  given Functor[List] with
    def fmap[A, B](f: A => B): List[A] => List[B] = _.map(f)

  given (using ExecutionContext): Functor[Future] with
    def fmap[A, B](f: A => B): Future[A] => Future[B] = _.map(f)
