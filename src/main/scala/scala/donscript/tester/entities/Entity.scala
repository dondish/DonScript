package scala.donscript.tester.entities

import scala.donscript.Scope

/**
  * An abstract entity
 *
  * @param args the arguments it is made of
  */
abstract class Entity(var args: Array[String], scope: Scope) {

  def +(entity: Entity): Entity = {
    Entity.assign(args ++ entity.args, scope)
  }

  def -(entity: Entity): Entity = {
    Entity.assign(args.diff(entity.args), scope)
  }

  def equals(entity: Entity): Boolean = {
    args == entity.args
  }

  /**
    * Used for insertion
    * @return the entity as a string
    */
  override def toString: String = args.mkString(" ")
}

/**
  * Companion object
  * It's purpose is to assign args to the correct entity for easier manipulation of data
  */
object Entity {
  def assign(args: Array[String], scope: Scope): Entity = {
    if (args.length == 1) {
      return Singleton(args, scope)
    }
    DonArray(args, scope)
  }
}