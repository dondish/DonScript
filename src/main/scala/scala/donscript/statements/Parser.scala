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
  def parse(statement: String, scope: Scope, vars: mutable.HashMap[String, Entity], commands: Map[String, Command]): ParseResult = {
    if (statement.isEmpty) {
      scope.backward()
    }
    val args = parseArgs(statement drop 1 split "(?!/)\\s+", vars)
    statement.charAt(0) match {
      case ':' =>
        Assignment(args, vars, scope).run
        ParseResult(scope, vars)
      case _ => ParseResult(scope, vars)
    }
  }

  def unescape(arg: String): String = {
    ""
  }

  /**
    * This parses the arguments and auto inserts entities while
    * @param args
    * @param vars
    * @return
    */
  def parseArgs(args: Array[String], vars: mutable.HashMap[String, Entity]): Array[String] = {
    val arrayBuffer: ArrayBuffer[String] = new ArrayBuffer[String]()
    for (arg <- args) {
      if (arg.startsWith(":") && vars.contains(arg drop 1)) {
        arrayBuffer ++= vars(arg drop 1).args
      } else {
        arrayBuffer += unescape(arg drop 1)
      }
    }
    arrayBuffer.toArray
  }
}
