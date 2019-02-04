package scala.donscript.statements

import scala.donscript.ScopeManager

/**
  * A statement is the core of DonScript
  * it starts with an identifier then is followed up with arguments and ends up with a semicolon
  * @example > Hello World!;# Prints out Hello World!;
  */
trait Statement {
  /**
    * Run the statement
    * @param args arguments
    * @param scope the current scope
    * @return the results from running
    */
  def run(args: Array[String], scope: ScopeManager): ParseResult
}
