package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon
import mememon.actions.{Source, Target}

/** Base contract defining the common attributes and behaviors for any combat unit in the game. */
trait Character extends Source with Target:
  /** Display name of the unit. */
  val name: String

  /** Current health points of the unit.
   *
   * @return the remaining health points, never below zero
   */
  def hp: Int

  /** Physical defense points mitigating incoming physical damage. */
  val defense: Int

  /** Base weight of the unit affecting its turn readiness. */
  val weight: Int

  /** Weapon currently equipped by the unit, if any.
   *
   * @return the equipped weapon, or None if the unit has no weapon
   */
  def currentWeapon: Option[Weapon]

  /** Computes the total physical attack damage dealt by this unit.
   *
   * @return attack power value based on equipped weapon or innate power
   */
  def attackDamage: Int

  /** Reduces the character's health points by an incoming damage value, without dropping below zero.
   *
   * @param amount quantity of damage to apply
   */
  def takeDamage(amount: Int): Unit

  /** Executes a physical attack action targeting an opponent character.
   *
   * @param target the defending unit receiving the attack
   */
  def attack(target: Character): Unit

  /** Calculates the total threshold required to complete the action bar for combat turns.
   *
   * @return maximum action bar threshold
   */
  def maxActionBar: Double
