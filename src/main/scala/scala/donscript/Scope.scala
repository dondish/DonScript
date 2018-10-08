package scala.donscript

import scala.collection.mutable
import scala.donscript.entities.Entity

class Scope {
  var scope: Int = 0
  val scopet = new mutable.ArrayStack[Int]()
  val scopepos = new mutable.ArrayStack[Int]()

  def backward(): Unit = {
    val pos = scopepos.pop
    val t = scopet.pop
    if (pos + 1 == t) {
      scope -= 1
    } else {
      scopepos.push(pos + 1)
      scopet.push(t)
    }
  }

  def forward(t: Int): Unit = {
    scope += 1
    scopet.push(t)
    scopepos.push(0)
  }
}
