package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Target}
import mememon.actions.spells.{HealingSpell, PurificationSpell}
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
  private val _healing: HealingSpell = new HealingSpell
  private val _purification: PurificationSpell = new PurificationSpell

  /** @inheritdoc */
  override def actions: List[Action] =
    _healing :: _purification :: super.actions

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByWhiteMage

  /** @inheritdoc */
  protected def dispatch(action: Action, target: Target): Unit =
    action.executeByWhiteMage(this, target)
