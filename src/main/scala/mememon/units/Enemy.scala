package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Target}

/** Represents a non-playable hostile creature on the battlefield.
 *
 * @param name monster name
 * @param initialHp starting health points
 * @param defense physical defense
 * @param weight monster weight
 * @param attack innate physical attack power
 */
class Enemy(
             name: String,
             initialHp: Int,
             defense: Int,
             weight: Int,
             val attack: Int
           ) extends AbstractGameUnit(name, initialHp, defense, weight):

  /** Enemies attack with their innate attack points.
   *
   * @return the enemy attack value
   */
  def attackDamage: Int = attack

  /** The action bar of an enemy depends only on its weight.
   *
   * @return the enemy weight
   */
  def maxActionBar: Double = weight.toDouble

  /** Loses 15% of the current health points plus half of the magical damage.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   */
  override def receivePurification(source: MagicCharacter, damage: Int): Unit =
    takeDamage(hp * 15 / 100 + damage / 2)

  /** @inheritdoc */
  def doAction(action: Action, target: Target): Unit =
    action.executeByEnemy(this, target)
