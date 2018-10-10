package scala.donscript

import scala.donscript.commands.Command
import scala.donscript.statements.Parser

/**
  * The main class
  * @param printer A function to print out data to like stdout
  * @param inputGiver A function to fetch input like stdin
  */
class Interpreter(private val printer: String => Unit, private val inputGiver: () => String) {
  var scope = new Scope(printer, inputGiver)
  var commands: Map[String, Command] = Map[String, Command]()

  /**
    * The main function.
    * Give it code and it will process it
    * @param code the code to process
    * @return an exit code
    */
  def interpret(code: String): Int = {
    for (statement <- code.split("(?<!/)\\s*(?<!/);\\s*")) {
      val result = Parser.parse(statement, scope, commands)
      scope = result.scope
    }
    0
  }
}
