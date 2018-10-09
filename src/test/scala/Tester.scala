import scala.donscript.Interpreter

class Tester extends App {
  val x: Interpreter = new Interpreter(x=>println(x), ()=>readLine())
  assert(x.interpret(":s 1") == 0)
  println(x.vars)
}
