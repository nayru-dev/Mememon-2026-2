package cl.uchile.dcc
package mememon.weapons

import mememon.items.Usable

/** Base contract representing any equipable weapon item in the game. */
trait Weapon extends Usable:
  /** Physical attack power contributed by the weapon. */
  val attack: Int

  /** Weight of the weapon affecting the holder's action bar speed. */
  val weight: Int
