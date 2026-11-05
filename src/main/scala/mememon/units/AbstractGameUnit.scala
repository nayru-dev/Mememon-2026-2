package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, Source}

/** Abstract implementation of the state and behavior shared by characters and enemies.
 *
 * @param name identifier name of the unit
 * @param initialHp initial health points before combat
 * @param defense physical defense value
 * @param weight base weight of the unit
 */
abstract class AbstractGameUnit(
                                 val name: String,
                                 initialHp: Int,
                                 val defense: Int,
                                 val weight: Int
                               ) extends GameUnit:
  private var _hp: Int = math.max(0, initialHp)

  /** @inheritdoc */
  def hp: Int = _hp

  /** @inheritdoc */
  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  /** @inheritdoc */
  def attack(target: GameUnit): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  /** @inheritdoc */
  def receiveAction(action: Action, source: Source): Unit =
    action.execute(source, this)
