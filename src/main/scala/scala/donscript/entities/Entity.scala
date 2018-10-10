package scala.donscript.entities

import scala.donscript.Scope



/**
  * An abstract entity
  *
  * @param args the arguments it is made of
  */
abstract class Entity(val args: Array[String], scope: Scope) {

  def +(entity: Entity): Entity = {
    Entity.assign(args ++ entity.args, scope)
  }

  def -(entity: Entity): Entity = {
    Entity.assign(args.diff(entity.args), scope)
  }

  def equals(entity: Entity): Boolean = {
    args sameElements entity.args
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
    args match {
      case Array(_) => Singleton(args, scope)
      case _ => DonArray(args, scope)
    }
  }
}