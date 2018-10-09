import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.donscript.Interpreter

@RunWith(classOf[JUnitRunner])
class Tester extends FunSuite {
  test("Basic tests") {
    val x: Interpreter = new Interpreter(x=>println(x), ()=>scala.io.StdIn.readLine())
    assert(x.interpret(":s 1") == 0)
    println(x.vars)
  }
}
