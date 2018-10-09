package scala.donscript.entities

import scala.donscript.Scope

/**
  * This is the Array, the most basic data structure
 *
  * @param args the arguments it is made of
  */
case class DonArray(override val args: Array[String], scope: Scope) extends Entity(args, scope) {
}
