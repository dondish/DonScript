package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.Scope
import scala.donscript.entities.Entity

trait Statement {
  var args: Array[String]

  var vars: mutable.HashMap[String, Entity]

  var scope: Scope

  def run: ParseResult
}
