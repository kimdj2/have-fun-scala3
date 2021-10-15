package answer.chapter3

import scala.language.implicitConversions

trait Result
case class EitherT[F[_], A, B](value: F[Either[A, B]])

/** Question: Implicit conversion */
object Question5:

  given Conversion[Either[Result, Result], Result] =
    _ match
      case Right(v) => v
      case Left(v)  => v

  given [F[_]](using Functor[F]): Conversion[F[Either[Result, Result]], F[Result]] =
    _.map(v => v)

  given [F[_]](using Functor[F]): Conversion[EitherT[F, Result, Result], F[Result]] =
    _.value.map(v => v)
