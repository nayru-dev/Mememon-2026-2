package cl.uchile.dcc
package mememon.units

import mememon.items.Usable
import mememon.weapons.Weapon

/** Contract for playable units, which can hold a weapon and carry usable objects. */
trait Character extends GameUnit:
  /** Weapon currently equipped by the character, if any.
   *
   * @return the equipped weapon, or None if the character has no weapon
   */
  def currentWeapon: Option[Weapon]

  /** Inventory of usable objects carried by this character.
   *
   * @return the list of objects currently stored
   */
  def inventory: List[Usable]

  /** Stores a usable object at the beginning of the inventory.
   *
   * @param item the usable object to store
   */
  def addToInventory(item: Usable): Unit

  /** Equips a weapon and becomes its owner, replacing the weapon currently equipped.
   *
   * @param weapon the weapon to equip
   * @throws mememon.exceptions.InvalidRequirementException if this character cannot equip the weapon
   */
  def equip(weapon: Weapon): Unit
