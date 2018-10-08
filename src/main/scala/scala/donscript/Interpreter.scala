package scala.donscript

import scala.collection.mutable
import scala.donscript.commands.Command
import scala.donscript.entities.Entity
import scala.donscript.statements.Parser

class Interpreter {
  val scope = new Scope
  var commands = Map[String, Command]()
  val vars = new mutable.HashMap[String, Entity]()

  def interpret(code: String): Int = {
    for (statement <- code.split("(?!/);")) {
      Parser.parse(statement, scope, vars, commands)

    }
  }
}
