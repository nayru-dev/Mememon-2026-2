package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Target}
import mememon.weapons.Weapon

/** Represents an Archer physical unit specialized in ranged combat.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Archer(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight):

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByArcher

  /** @inheritdoc */
  protected def dispatch(action: Action, target: Target): Unit =
    action.executeByArcher(this, target)
