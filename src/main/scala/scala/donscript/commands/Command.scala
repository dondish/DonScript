package scala.donscript.commands

import scala.donscript.Scope

/**
  * A command is like a statement but unlike statements people can add commands.
  * This adds extensibility to DonScript
  */
trait Command {
  /**
    * Execute the command
    * @param args the arguments
    * @param scope the scope
    */
  def execute(args: Array[String], scope: Scope): Unit
}
