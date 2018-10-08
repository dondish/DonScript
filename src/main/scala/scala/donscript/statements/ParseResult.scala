package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.entities.Entity

case class ParseResult(scope: Int, scopet: Int, scopepos: Int, vars: mutable.HashMap[String, Entity]) {}
