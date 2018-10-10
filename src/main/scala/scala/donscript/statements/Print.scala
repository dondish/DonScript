package scala.donscript.statements
import scala.donscript.Scope
import scala.donscript.entities.Entity

case class Print() extends Statement {
  override def run(args: Array[String], scope: Scope): ParseResult = {
    if (args.length > 0 && args(0) == ">") {
      scope.printer(Entity.assign(args drop 1, scope).toString + "\n")
    } else {
      scope.printer(Entity.assign(args, scope).toString)
    }
    ParseResult(scope)
  }
}
