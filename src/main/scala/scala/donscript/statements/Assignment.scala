package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.entities.Entity

/**
  * This is the Assignment statement
  * @param args arguments to the statement
  * @param vars the variables
  */
case class Assignment(args: Array[String], vars: mutable.HashMap[String, Entity]) extends Statement {
  override def run: Int = {
    if (args.isEmpty)
      return 0
    val name = args(0)
    val value = Entity.assign(args.slice(1, args.length))
    vars += ((name, value))
    0
  }
}
