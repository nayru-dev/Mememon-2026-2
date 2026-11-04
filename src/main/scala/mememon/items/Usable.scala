package cl.uchile.dcc
package mememon.items

/** Contract for any object a character can store in its inventory and use. */
trait Usable:
  /** Display name of the usable object. */
  val name: String
