package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon

/** Represents a Black Mage unit specializing in offensive destructive spells.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 * @param mana starting mana pool
 */
class BlackMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana):

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByBlackMage
