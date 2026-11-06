package cl.uchile.dcc
package mememon.weapons

/** Contract representing weapons capable of channeling magical damage. */
trait MagicWeapon extends Weapon:
  /** Magical attack power provided by this weapon. */
  val magicAttack: Int
