import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.donscript.Interpreter
import scala.donscript.entities.{DonArray, Singleton}

@RunWith(classOf[JUnitRunner])
class Tester extends FunSuite {
  test("Assignment and Injection") {
    val x: Interpreter = new Interpreter(print, scala.io.StdIn.readLine)
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
    val x: Interpreter = new Interpreter(print, scala.io.StdIn.readLine)
     x.interpret(":as Testing Variable Printing!;> :as /n;>> :as")
  }

  test("Exit at scope -1") {
    val x: Interpreter = new Interpreter(print, scala.io.StdIn.readLine)
    x.interpret(";>> Should not print this!;;")
  }
  test("Conditional") {
    val x: Interpreter = new Interpreter(print, scala.io.StdIn.readLine)

    // Number Equality

    x.interpret("?1 = 1;>> Equals1 V;;>> Equals1 X;;>> After If Block V;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 = 2;>> Equals2 X;;>> Equals2 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 != 1;>> NEquals1 X;;>> NEquals1 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 != 2;>> NEquals2 V;;>> NEquals2 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 > 2;>> Bigthen1 X;;>> Bigthen1 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 < 2;>> Bigthen2 V;;>> Bigthen2 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    // Array Equality

    x.interpret("?1 2 3 = 1 2 3;>> Equals3 V;;>> Equals3 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 2 3 = 1 3 2;>> Equals4 X;;>> Equals4 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 2 3 != 1 2 3;>> NEquals3 X;;>> NEquals3 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 2 3 != 1 3 2;>> NEquals4 V;;>> NEquals4 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 2 3 > 2 1 3;>> Bigthen3 X;;>> Bigthen3 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 2 3 < 2 1 3;>> Bigthen4 V;;>> Bigthen4 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 2 >> 1 2 3;>> Include1 X;;>> Include1 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 2 3 >> 1 2;>> Include2 V;;>> Include2 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    x.interpret("?1 2 3 << 1 2;>> Exclude1 X;;>> Exclude1 V;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)
    x.interpret("?1 2 << 1 2 3;>> Exclude2 V;;>> Exclude2 X;;")
    assert(x.scope.scope == 0 && x.scope.scopeTypes.top == 1 && x.scope.scopePositions.top == 0)

    // Nested Conditionals

    x.interpret(
      "?1 = 1;" +
        "?1 = 1;" +
          ">> Nested1 V;" +
        ";" +
          ">> Nested1 X;" +
        ";" +
        ">> Nested2 V;" +
      ";" +
        ">> Nested2 X;" +
      ";")
  }
}
