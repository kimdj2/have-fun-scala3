package answer.chapter3

/** Question: ExtensionMethods */
object Question3:

  extension [T: Ord](x: T)
    def < (y: T)(using ord: Ord[T]): Boolean =
      ord.compare(x, y) match
        case -1 => true
        case _  => false

    def > (y: T)(using ord: Ord[T]): Boolean =
      ord.compare(x, y) match
        case 1 => true
        case _ => false

  extension [T](list: List[T])
    def max(using Ord[T]): T =
      Question2.max(list)
