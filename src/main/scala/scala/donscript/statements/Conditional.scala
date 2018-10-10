package scala.donscript.statements
import scala.donscript.Scope
import scala.donscript.entities.Entity

/**
  * The conditional statement
  */
case class Conditional() extends Statement {
  /**
    * Run the statement
    * @param args arguments
    * @param scope the current scope
    * @return the results from running
    */
  override def run(args: Array[String], scope: Scope): ParseResult = {
    if (args.isEmpty) {
      scope.forward(2, condition = false)
    }
    val (left, right) = args.span(x => x != "=" && x != "!=" && x != ">" && x != "<" && x != ">>" && x != "<<")
    if (right.length == 0) {
      scope.forward(2)
    } else {
      right(0) match {
        case "=" => scope.forward(2, Entity.assign(left, scope) == Entity.assign(right drop 1, scope))
        case "!=" => scope.forward(2, Entity.assign(left, scope) != Entity.assign(right drop 1, scope))
        case ">" => scope.forward(2, Entity.assign(left, scope) > Entity.assign(right drop 1, scope))
        case "<" => scope.forward(2, Entity.assign(left, scope) < Entity.assign(right drop 1, scope))
        case ">>" => scope.forward(2, (right drop 1 diff left).isEmpty)
        case "<<" => scope.forward(2, (left diff (right drop 1)).isEmpty)
      }
    }
    ParseResult(scope, 0)
  }
}