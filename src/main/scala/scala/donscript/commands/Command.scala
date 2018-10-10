package scala.donscript.commands

import scala.donscript.Scope
import scala.donscript.statements.ParseResult

/**
  * A command is like a statement but unlike statements people can add commands.
  * This adds extensibility to DonScript
  */
trait Command {
  /**
    * Execute the command
    * @param args the arguments
    * @param scope the scope
    * @return Just like a statement, a command returns a result to see if to stop interpreting or not
    */
  def execute(args: Array[String], scope: Scope): ParseResult
}
