package chapter3

trait Monoid[T]:
  def add(x: T, y: T): T
  def unit: T
  extension (x: T) def |+|(y: T): T = add(x, y)
