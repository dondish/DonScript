package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.Scope
import scala.donscript.entities.Entity

trait Statement {
  def run(args: Array[String], vars: mutable.HashMap[String, Entity], scope: Scope): ParseResult
}
