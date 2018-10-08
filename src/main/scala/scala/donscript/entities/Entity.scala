package scala.donscript.entities

import scala.donscript.Scope

/**
  * An abstract entity
 *
  * @param args the arguments it is made of
  */
abstract class Entity(var args: Array[String], scope: Scope) {

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