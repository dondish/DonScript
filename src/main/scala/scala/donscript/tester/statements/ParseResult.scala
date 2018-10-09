package scala.donscript.tester.statements

import scala.collection.mutable
import scala.donscript.Scope
import scala.donscript.entities.Entity

case class ParseResult(scope: Scope, vars: mutable.HashMap[String, Entity]) {}
