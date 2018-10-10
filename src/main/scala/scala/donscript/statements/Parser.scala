package scala.donscript.statements

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.donscript.Scope
import scala.donscript.commands.Command
import scala.donscript.entities.Entity

/**
  * This object parses data and returns the result needed
  */
object Parser {
  /**
    * Parses and executes the statement
    * @param statement the statement given
    * @return amount to change the scope
    */
  def parse(statement: String, scope: Scope,  commands: Map[String, Command]): ParseResult = {
    if (statement.isEmpty) {
      scope.backward()
    }
    val args = parseArgs(statement drop 1 split "(?<!/)\\s+", scope)
    statement.charAt(0) match {
      case ':' =>
        Assignment().run(args, scope)
        ParseResult(scope)
      case _ => ParseResult(scope)
    }
  }

  /**
    * Unescapes argument
    * @param arg the argument
    * @return a value unescaped
    */
  def unescape(arg: String): String = {
    val stringBuilder: mutable.StringBuilder = new mutable.StringBuilder()
    var prev: Boolean = false
    for (chr <- arg.toCharArray) {
      if (prev) {
        if (chr == 't') {
          stringBuilder.append('\t')
        } else if (chr == 'n') {
          stringBuilder.append('\n')
        } else {
          stringBuilder.append(chr)
        }
        prev = false
      } else {
        if (chr == '/') {
          prev = true
        } else {
          stringBuilder.append(chr)
        }
      }
    }
    stringBuilder.toString()
  }

  /**
    * This parses the arguments and auto inserts entities
    * @param args the arguments
    * @param vars the map of vars to entities
    * @return
    */
  def parseArgs(args: Array[String], scope: Scope): Array[String] = {
    val arrayBuffer: ArrayBuffer[String] = new ArrayBuffer[String]()
    for (arg <- args) {
      if (arg.startsWith(":")) {
        arrayBuffer ++= scope.getVar(arg drop 1).args
      } else  {
        arrayBuffer += unescape(arg)
      }
    }
    arrayBuffer.toArray
  }
}
