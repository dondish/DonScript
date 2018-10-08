package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.commands.Command
import scala.donscript.entities.Entity

/**
  * This object parses the statement to type and then executes the statement
  */
object Parser {
  /**
    * Parses and executes the statement
    * @param statement the statement given
    * @return amount to change the scope
    */
  def parse(statement: String, scope: Int, scopet: Int, scopepos: Int, vars: mutable.HashMap[String, Entity], commands: Map[String, Command]): Int = {
    if (statement.isEmpty)
      return -1
    val args = statement drop 1 split "(?!/)\\s+"
    statement.charAt(0) match {
      case ':' =>
        Assignment(args)
        0
      case _ => 0
    }
  }

  /**
    * This parses the arguments and auto inserts entities while
    * @param args
    * @param vars
    * @return
    */
  def parseArgs(args: Array[String], vars: mutable.HashMap[String, Entity]): Array[String] = {

  }
}
