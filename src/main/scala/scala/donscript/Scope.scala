package scala.donscript

import scala.collection.mutable
import scala.donscript.entities.Entity

/**
  * The scope manager
  * @param printer stdout
  * @param inputGiver stdin
  */
class Scope(val printer: String => Unit, val inputGiver: () => String) {
  var scope: Int = 0
  val scopet = new mutable.ArrayStack[Int]()
  val scopepos = new mutable.ArrayStack[Int]()
  val vars = new mutable.ArrayStack[mutable.HashMap[String, Entity]]()
  vars.push(new mutable.HashMap[String, Entity]())

  /**
    * Goes backwards in the scope
    * if -> else
    * else -> out
    * block -> out
    */
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

  /**
    * Adds another block
    * @param t the block type
    */
  def forward(t: Int): Unit = {
    scope += 1
    scopet.push(t)
    scopepos.push(0)
  }

  /**
    * Get a variable
    * First it looks at an available now at the scope level and then goes down to all scopes
    * @param name variable name
    * @return the variable's value
    */
  def getVar(name: String): Entity = {
    for (stack <- vars)
      if (stack.contains(name))
        return stack(name)
    Entity.assign(Array(), this)
  }

  /**
    * Set a variable
    * First it looks for existing one (this scope down) to update
    * If non found it adds another variable to this scope level
    * @param name variable name
    * @param entity variable value
    */
  def setVar(name: String, entity: Entity): Unit = {
    for (stack <- vars)
      if (stack.contains(name)) {
        stack.update(name, entity)
        return
      }
    vars.top += ((name, entity))
  }
}
