package scala.donscript

import scala.donscript.commands.Command
import scala.donscript.statements.Parser

/**
  * The main class
  * @param printer A function to print out data to like stdout
  * @param inputGiver A function to fetch input like stdin
  */
class Interpreter(private val printer: String => Unit, private val inputGiver: () => String) {
  var scope = new ScopeManager(printer, inputGiver)
  var commands: Map[String, Command] = Map[String, Command]()

  /**
    * The main function.
    * Give it code and it will process it
    * @param code the code to process
    * @return an exit code
    */
  def interpret(code: String): Int = {
    for (statement <- code.split("(?<!/);\\s*", -1).dropRight(if (code.endsWith(";")) 1 else 0)) {
      val result = Parser.parse(statement, scope, commands)
      if (result.exit != 0) {
        return result.exit
      }
    }
    0
  }
}
