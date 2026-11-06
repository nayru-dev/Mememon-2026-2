package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Target}
import mememon.weapons.Weapon

/** Represents a Thief physical unit specialized in agility.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense physical defense value
 * @param weight character weight
 */
class Thief(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight):

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByThief

  /** @inheritdoc */
  protected def dispatch(action: Action, target: Target): Unit =
    action.executeByThief(this, target)
