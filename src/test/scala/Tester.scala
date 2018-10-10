import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.donscript.Interpreter
import scala.donscript.entities.{DonArray, Singleton}

@RunWith(classOf[JUnitRunner])
class Tester extends FunSuite {
  test("Basic tests") {
    val x: Interpreter = new Interpreter(x=>println(x), ()=>scala.io.StdIn.readLine())
    x.interpret(":as 1") // basic assignment to a singleton
    assert(x.vars("as").getClass == classOf[Singleton] && x.vars("as").args.sameElements(Array("1")))
    x.interpret(":as 1 2 3") // multiple values
    assert(x.vars("as").isInstanceOf[DonArray] && x.vars("as").args.sameElements(Array("1", "2", "3")))
    x.interpret(":as 1/ 2/ 3")// a singleton
    assert(x.vars("as").isInstanceOf[Singleton] && x.vars("as").args.sameElements(Array("1 2 3")))
    x.interpret(":as :as 4")
    println(x.vars("as"))
  }
}
