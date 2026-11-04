package cl.uchile.dcc
package mememon.weapons

/** Base contract representing any equipable weapon item in the game. */
trait Weapon:
  /** Unique display name of the weapon. */
  val name: String

  /** Physical attack power contributed by the weapon. */
  val attack: Int

  /** Weight of the weapon affecting the holder's action bar speed. */
  val weight: Int
