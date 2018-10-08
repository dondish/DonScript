package scala.donscript.statements

import scala.collection.mutable
import scala.donscript.entities.Entity

trait Statement {
  var args: Array[String]

  def vars: mutable.HashMap[String, Entity]

  def run: Int
}
