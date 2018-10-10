package scala.donscript.statements

import scala.donscript.Scope
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
  override def run(args: Array[String], scope: Scope): ParseResult = {
    if (args.isEmpty)
      return ParseResult(scope, 0)
    val name = args(0)
    val value = Entity.assign(args.slice(1, args.length), scope)
    scope.setVar(name, value)
    ParseResult(scope, 0)
  }
}
