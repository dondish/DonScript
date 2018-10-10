import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.donscript.Interpreter
import scala.donscript.entities.{DonArray, Singleton}

@RunWith(classOf[JUnitRunner])
class Tester extends FunSuite {
  test("Assignment and Injection") {
    val x: Interpreter = new Interpreter(x=>println(x), ()=>scala.io.StdIn.readLine())
    x.interpret(":as 1") // basic assignment to a singleton
    assert(x.scope.getVar("as").getClass == classOf[Singleton] && x.scope.getVar("as").args.sameElements(Array("1")))
    x.interpret(":as 1 2 3") // multiple values
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array("1", "2", "3")))
    x.interpret(":as 1/ 2/ 3")// a singleton
    assert(x.scope.getVar("as").isInstanceOf[Singleton] && x.scope.getVar("as").args.sameElements(Array("1 2 3")))
    x.interpret(":as :as 4") // basic injection
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array("1 2 3", "4")))
    x.interpret(":as /  ") // basic double space but one is escaped
    assert(x.scope.getVar("as").isInstanceOf[Singleton] && x.scope.getVar("as").args.sameElements(Array(" ")))
    x.interpret(":as /  / ") // double space preceded by a forward slash and followed by an escaped space
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array(" ", " ")))
    x.interpret(":as /  /  ;") // adding a space and a semicolon
    assert(x.scope.getVar("as").isInstanceOf[DonArray] && x.scope.getVar("as").args.sameElements(Array(" ", " ")))
  }

  test("Printing") {
    val x: Interpreter = new Interpreter(x=>print(x), ()=>scala.io.StdIn.readLine())
     x.interpret(":as Testing Variable Printing!;> :as /n;>> :as")
  }

  test("Exit at scope -1") {
    val x: Interpreter = new Interpreter(x=>print(x), ()=>scala.io.StdIn.readLine())
    x.interpret(";>> Should not print this!")
  }
}
