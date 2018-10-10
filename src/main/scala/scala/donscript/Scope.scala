package scala.donscript

import scala.collection.mutable
import scala.donscript.entities.Entity

class Scope {
  var scope: Int = 0
  val scopet = new mutable.ArrayStack[Int]()
  val scopepos = new mutable.ArrayStack[Int]()
  val vars = new mutable.ArrayStack[mutable.HashMap[String, Entity]]()
  vars.push(new mutable.HashMap[String, Entity]())

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

  def getVar(name: String): Entity = {
    for (stack <- vars)
      if (stack.contains(name))
        return stack(name)
    Entity.assign(Array(), this)
  }

  def setVar(name: String, entity: Entity): Unit = {
    for (stack <- vars)
      if (stack.contains(name)) {
        stack.update(name, entity)
        return
      }
    vars.top += ((name, entity))
  }
}
