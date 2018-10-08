package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.Scope
import scala.donscript.entities.Entity

/**
  * This is the Assignment statement
  * @param args arguments to the statement
  * @param vars the variables
  */
case class Assignment(args: Array[String], var vars: mutable.HashMap[String, Entity], scope: Scope) extends Statement {
  override def run: ParseResult = {
    if (args.isEmpty)
      return ParseResult(scope, vars)
    val name = args(0)
    val value = Entity.assign(args.slice(1, args.length), scope)
    vars += ((name, value))
    ParseResult()
  }
}
