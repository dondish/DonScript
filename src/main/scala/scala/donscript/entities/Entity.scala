package scala.donscript.entities

import scala.donscript.Scope



/**
  * An abstract entity
  *
  * @param args the arguments it is made of
  */
abstract class Entity(val args: Array[String], scope: Scope) extends Ordered[Entity] {

  def +(entity: Entity): Entity = {
    Entity.apply(args ++ entity.args, scope)
  }

  def -(entity: Entity): Entity = {
    Entity.apply(args.diff(entity.args), scope)
  }

  override def equals(o: Any): Boolean = {
    o match {
      case entity: Entity => args sameElements entity.args
      case _ => false
    }
  }

  override def compare(that: Entity): Int = {
    if (args.length - that.args.length != 0) return args.length - that.args.length
    for (arg <- args;thatarg <- that.args) {
      if (arg.compareTo(thatarg) != 0) {
        return arg.compareTo(thatarg)
      }
    }
    0
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
  def apply(args: Array[String], scope: Scope): Entity = {
    args match {
      case Array(_) => Singleton(args, scope)
      case _ => DonArray(args, scope)
    }
  }
}