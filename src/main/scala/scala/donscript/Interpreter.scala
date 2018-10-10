package scala.donscript

import scala.collection.mutable
import scala.donscript.commands.Command
import scala.donscript.entities.Entity
import scala.donscript.statements.Parser

class Interpreter(private val printer: String => Unit, private val inputGiver: () => String) {
  var scope = new Scope
  var commands: Map[String, Command] = Map[String, Command]()

  def interpret(code: String): Int = {
    for (statement <- code.split("(?!/);")) {
      val result = Parser.parse(statement, scope, commands)
      scope = result.scope
    }
    0
  }
}
