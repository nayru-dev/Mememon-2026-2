package cl.uchile.dcc
package mememon.units

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
