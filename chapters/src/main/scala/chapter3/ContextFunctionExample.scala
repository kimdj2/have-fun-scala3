package chapter3

import scala.collection.mutable.ArrayBuffer

object ContextFunctionExample:
  /** Template model for html tags */
  import Tag.Indent
  trait Tag:
  
    /** List of child tags */
    val childs = new ArrayBuffer[Tag]
  
    /** Append new child tag */
    def add(t: Tag): Unit = childs += t
  
    /** Transform to Html template string */
    def toHtmlString: Indent ?=> String
  
  /** Companion object */
  object Tag:
    /** Opaque type: Indent */
    opaque type Indent = Int
    object Indent:
      def apply(v: Int): Indent = v
      extension (lhs: Indent)
        /** Translate indent number to string space */
        def toSpace: String = "  " * lhs
        /** Adder with `Int` value */
        def +(rhs: Int): Indent = lhs + rhs
  
  /** Model for <html> tag */
  class Html extends Tag:
    def toHtmlString = (indent: Indent) ?=>
      val childIndent = indent + 1
        indent.toSpace + "<html>"
      + childs.map(child => child.toHtmlString(using childIndent)).mkString("\n", "\n", "\n")
      + indent.toSpace + "</html>"
  
  /** Model for <div> tag */
  class Div extends Tag:
    def toHtmlString = (indent: Indent) ?=>
      val childIndent = indent + 1
        indent.toSpace + "<div>"
      + childs.map(child => child.toHtmlString(using childIndent)).mkString("\n", "\n", "\n")
      + indent.toSpace + "</div>"
  
  /** Model for <p> tag */
  class P(body: String) extends Tag:
    def toHtmlString = (indent: Indent) ?=>
      s"${indent.toSpace}<p>${body}</p>"
  
  /** Model for <img> tag */
  class Img(src: String) extends Tag:
    def toHtmlString = (indent: Indent) ?=>
      s"${indent.toSpace}<img src=\"${src}\" />"
  
  /** Represent <html> tag */
  def html(init: Html ?=> Unit): Html =
    given h: Html = Html()
    init
    h
  
  /** Represent <div> tag */
  def div(init: Div ?=> Unit)(using t: Tag): Unit =
    given d: Div = Div()
    init
    t.add(d)
  
  /** Represent <p> tag */
  def p(body: String)(using t: Tag): Unit =
    t.add(P(body))
  
  /** Represent <img> tag */
  def img(src: String)(using t: Tag): Unit =
    t.add(Img(src))
  
  @main def runHtml(): Unit =
    given Indent = Indent(0)
    val template = html {
      div {
        p("Hello")
        img("/assets/images/hoge.jpg")
      }
    }
    println(template.toHtmlString)
