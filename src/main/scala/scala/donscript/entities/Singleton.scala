package scala.donscript.entities

import scala.donscript.Scope

/**
  * While it is made of arrays the singleton's purpose is for value on value checking
  * Singleton is the only data entity alas than the array
 *
  * @param args the arguments it is made of
  */
case class Singleton(override var args: Array[String], scope: Scope) extends Entity(args, scope) {

}
