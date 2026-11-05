package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Target}
import mememon.actions.spells.{MeteorSpell, ThunderSpell}
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
  private val _thunder: ThunderSpell = new ThunderSpell
  private val _meteor: MeteorSpell = new MeteorSpell

  /** @inheritdoc */
  override def actions: List[Action] =
    _thunder :: _meteor :: super.actions

  /** @inheritdoc */
  protected def canEquip(weapon: Weapon): Boolean = weapon.canBeEquippedByBlackMage

  /** @inheritdoc */
  protected def dispatch(action: Action, target: Target): Unit =
    action.executeByBlackMage(this, target)
