package cl.uchile.dcc
package mememon.units

/** Contract for units capable of channeling and spending mana points. */
trait MagicCharacter extends Character:
  /** Current or maximum mana points of the magical unit. */
  val mana: Int
