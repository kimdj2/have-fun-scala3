package chapter3

trait MonoidGiven:
  given intMonoid: Monoid[Int] with
    def add(x: Int, y: Int): Int = x + y
    def unit: Int = 0

  given stringMonoid: Monoid[String] with
    def add(x: String, y: String): String = x + y
    def unit: String = ""

  given optionMonoid[A](using m: Monoid[A]): Monoid[Option[A]] with
    def add(x: Option[A], y: Option[A]): Option[A] =
      (x, y) match
        case (Some(x1), Some(y1)) => Some(m.add(x1, y1))
        case (Some(x1), None)     => Some(x1)
        case (None,     Some(y1)) => Some(y1)
        case _                    => None
    def unit: Option[A] = None

  given listMonoid[A]: Monoid[List[A]] with
    def add(x: List[A], y: List[A]): List[A] = x ++ y
    def unit: List[A] = List()
