package scala.donscript

import scala.collection.mutable
import scala.donscript.entities.Entity

/**
  * The scope manager
  * @param printer stdout
  * @param inputGiver stdin
  */
class Scope(val printer: String => Unit, val inputGiver: () => String) {
  /**
    * The scope types
    */
  val scopeTypes = new mutable.ArrayStack[Int]()

  scopeTypes.push(1) // default scope type is 1 for a normal block

  /**
    * The positions in each scope
    */
  val scopePositions = new mutable.ArrayStack[Int]()

  scopePositions.push(0) // default scope position is 0

  /**
    * Variables per scope
    */
  val vars = new mutable.ArrayStack[mutable.HashMap[String, Entity]]()

  vars.push(new mutable.HashMap[String, Entity]()) // Add the global scope

  /**
    * The current condition of a scope
    */
  val conditions = new mutable.ArrayStack[Boolean]()
  conditions.push(true)

  def scope: Int = scopeTypes.length - 1

  /**
    * Goes backwards in the scope
    * if -> else
    * else -> out
    * block -> out
    * @return whether should exit completely
    */
  def backward(): Boolean = {
    val pos = scopePositions.pop
    val t = scopeTypes.pop
    val condition = conditions.pop
    if (pos + 1 == t && scope < 0) {
      true
    } else if (pos + 1 != t){
      conditions.push(!condition)
      scopePositions.push(pos + 1)
      scopeTypes.push(t)
      false
    } else {
      false
    }
  }

  /**
    * Adds another block
    * @param t the block type
    * @param condition Whether the following statement should be executed
    */
  def forward(t: Int, condition: Boolean = true): Unit = {
    scopeTypes.push(t)
    scopePositions.push(0)
    conditions.push(condition)
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
    Entity.apply(Array(), this)
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

  /**
    * @return Whether the next statement should even run
    */
  def shouldRun: Boolean = conditions.top
}
