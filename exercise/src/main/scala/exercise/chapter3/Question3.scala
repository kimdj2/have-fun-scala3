package exercise.chapter3

/** Question: ExtensionMethods */
object Question3:

  extension [T](x: T)
    def < (y: T): Boolean = ???
    def > (y: T): Boolean = ???

  extension [T](list: List[T])
    def max: T = ???

end Question3
