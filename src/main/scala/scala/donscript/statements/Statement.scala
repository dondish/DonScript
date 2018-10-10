package scala.donscript.statements

import scala.donscript.Scope

trait Statement {
  def run(args: Array[String], scope: Scope): ParseResult
}
