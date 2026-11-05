package cl.uchile.dcc
package mememon.weapons

import mememon.items.Usable
import mememon.units.Character

/** Base contract representing any equipable weapon item in the game. */
trait Weapon extends Usable:
  /** Physical attack power contributed by the weapon. */
  val attack: Int

  /** Weight of the weapon affecting the holder's action bar speed. */
  val weight: Int

  /** Character that owns this weapon, if any.
   *
   * @return the owner, or None if the weapon has no owner
   */
  def owner: Option[Character]

  /** Assigns a character as the owner of this weapon, replacing any previous owner.
   *
   * @param character the new owner
   */
  def setOwner(character: Character): Unit
