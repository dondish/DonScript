package scala.donscript.statements
import scala.donscript.Scope
import scala.donscript.entities.Entity

/**
  * The print statement
 */
case class Print() extends Statement {
  /**
    * Run the statement
    * @param args arguments
    * @param scope the current scope
    * @return the results from running
    */
  override def run(args: Array[String], scope: Scope): ParseResult = {
    if (args.length > 0 && args(0) == ">") {
      scope.printer(Entity.assign(args drop 1, scope).toString + "\n")
    } else {
      scope.printer(Entity.assign(args, scope).toString)
    }
    ParseResult(0)
  }
}
