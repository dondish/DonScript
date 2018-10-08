package scala.donscript

import scala.collection.mutable
import scala.donscript.statements.Parser

class Interpreter {

  def interpret(code: String): Int = {
    var scope = 0
    var scopepos = 0
    var scopet = 0
    var commands = Map()
    for (statement <- code.split("(?!/);")) {
      Parser.parse(statement)

    }
  }
}
