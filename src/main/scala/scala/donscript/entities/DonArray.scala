package scala.donscript.entities

import scala.donscript.ScopeManager

/**
  * This is the Array, the most basic data structure
 *
  * @param args the arguments it is made of
  */
case class DonArray(override val args: Array[String], scope: ScopeManager) extends Entity(args, scope) {
}
