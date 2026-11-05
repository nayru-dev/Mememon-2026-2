package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon

/** Represents a White Mage unit specializing in restorative magic.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 * @param mana starting mana pool
 */
class WhiteMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana):

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByWhiteMage
