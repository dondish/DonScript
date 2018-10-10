package scala.donscript.entities

import scala.donscript.Scope

/**
  * While it is made of arrays the singleton's purpose is for value on value checking
  * Singleton is the only data entity alas than the array
 *
  * @param args the arguments it is made of
  */
case class Singleton(override val args: Array[String], scope: Scope) extends Entity(args, scope) {

  def getValue: Either[Double, String] = {
    val decimal = """^(\d+\.?\d*)$""".r
    args(0) match {
      case decimal(num) => Left(num.toDouble)
      case x => Right(x)
    }
  }

  def +(singleton: Singleton): Entity = {
    (getValue, singleton.getValue) match {
      case (Left(x), Left(y)) => Entity.assign(Array((x+y).toString), scope)
      case (_, _) => super.+(singleton)
    }
  }

  def -(singleton: Singleton): Entity = {
    (getValue, singleton.getValue) match {
      case (Left(x), Left(y)) => Entity.assign(Array((x-y).toString), scope)
      case (_, _) => super.-(singleton)
    }
  }

  def compare(that: Singleton): Int = {
    (getValue, that.getValue) match {
      case (Left(x), Left(y)) => x.compareTo(y)
      case (_, _) => super.compare(that)
    }
  }
}
