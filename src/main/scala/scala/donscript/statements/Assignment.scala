package scala.donscript.statements

import scala.donscript.ScopeManager
import scala.donscript.entities.Entity

/**
  * This is the Assignment statement
  */
case class Assignment() extends Statement {
  /**
    * Run the statement
    * @param args arguments to the statement
    * @param scope the scope
    * @return the result
    */
  override def run(args: Array[String], scope: ScopeManager): ParseResult = {
    if (args.isEmpty)
      return ParseResult(0)
    val name = args(0)
    val value = Entity(args.slice(1, args.length), scope)
    scope.setVar(name, value)
    ParseResult(0)
  }
}
