import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.donscript.Interpreter
import scala.donscript.entities.{DonArray, Singleton}

@RunWith(classOf[JUnitRunner])
class Tester extends FunSuite {
  test("Assignment and injection") {
    val x: Interpreter = new Interpreter(x=>println(x), ()=>scala.io.StdIn.readLine())
    x.interpret(":as 1") // basic assignment to a singleton
    assert(x.scope.getVar("as").getClass == classOf[Singleton] && x.scope.getVar("as").args.sameElements(Array("1")))
    x.interpret(":as 1 2 3") // multiple values
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array("1", "2", "3")))
    x.interpret(":as 1/ 2/ 3")// a singleton
    assert(x.scope.getVar("as").isInstanceOf[Singleton] && x.scope.getVar("as").args.sameElements(Array("1 2 3")))
    x.interpret(":as :as 4") // basic injection
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array("1 2 3", "4")))
  }
}
