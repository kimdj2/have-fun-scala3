package answer.chapter3

import scala.collection.mutable.ArrayBuffer

object Question6:

  class Table:
    val children = new ArrayBuffer[Row]
    def add(r: Row): Unit = children += r
    def toHtmlString

  class Row:
    val children = new ArrayBuffer[Cell]
    def add(c: Cell): Unit = children += c

  case class Cell(elem: String)

  def table(init: Table ?=> Unit): Table =
    given t: Table = Table()
    init
    t

  def row(init: Row ?=> Unit) = (t: Table) ?=>
    given r: Row = Row()
    init
    t.add(r)

  def cell(str: String) = (r: Row) ?=>
    r.add(Cell(str))
