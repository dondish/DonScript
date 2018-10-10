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
  test("Conditional") {
    val x: Interpreter = new Interpreter(x=>print(x), ()=>scala.io.StdIn.readLine())

    // Number Equality

    x.interpret("?1 = 1;>> Equals1 V;;>> Equals1 X;;>> After if block")
    x.interpret("?1 = 2;>> Equals2 X;;>> Equals2 V;;")

    x.interpret("?1 != 1;>> NEquals1 X;;>> NEquals1 V;;")
    x.interpret("?1 != 2;>> NEquals2 V;;>> NEquals2 X;;")

    x.interpret("?1 > 2;>> Bigthen1 X;;>> Bigthen1 V;;")
    x.interpret("?1 < 2;>> Bigthen2 V;;>> Bigthen2 X;;")

    // Array Equality

    x.interpret("?1 2 3 = 1 2 3;>> Equals1 V;;>> Equals1 X;;")
    x.interpret("?1 2 3 = 1 3 2;>> Equals2 X;;>> Equals2 V;;")

    x.interpret("?1 2 3 != 1 2 3;>> Should not print this;;>> Should print this;;")
    x.interpret("?1 2 3 != 1 3 2;>> Should print this;;>> Should not print this;;")

    x.interpret("?1 2 3 > 2 1 3;>> Should not print this;;>> Should print this;;")

    x.interpret("?1 2 3 < 2 1 3;>> Should print this;;>> Should not print this;;")

    x.interpret("?1 2 >> 1 2 3;>> Should not print this;;>> Should print this;;")
    x.interpret("?1 2 3 >> 1 2;>> Should print this;;>> Should not print this;;")

    x.interpret("?1 2 3 << 1 2;>> Should not print this;;>> Should print this;;")
    x.interpret("?1 2 << 1 2 3;>> Should print this;;>> Should not print this;;")

    // Nested Conditionals

    x.interpret(
      "?1 = 1;" +
        "?1 = 1;" +
          ">> Should print this;" +
        ";" +
          ">> Should not print this;" +
        ";" +
        ">> Should not print this;" +
      ";" +
        ">> Should not print this;" +
      ";")
  }
}
