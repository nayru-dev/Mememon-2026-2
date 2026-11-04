package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon
import mememon.actions.{Action, Source}

/** Represents a non-playable hostile creature on the battlefield.
 *
 * @param name monster name
 * @param initialHp starting health points
 * @param defense physical defense
 * @param weight monster weight
 * @param attack innate physical attack power
 */
class Enemy(
             val name: String,
             initialHp: Int,
             val defense: Int,
             val weight: Int,
             val attack: Int
           ) extends Character:
  private var _hp: Int = math.max(0, initialHp)

  /** Enemies can never hold a weapon.
   *
   * @return always None
   */
  def currentWeapon: Option[Weapon] = None

  /** @inheritdoc */
  def hp: Int = _hp

  /** Enemies attack with their innate attack points.
   *
   * @return the enemy attack value
   */
  def attackDamage: Int = attack

  /** @inheritdoc */
  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  /** @inheritdoc */
  def attack(target: Character): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  /** The action bar of an enemy depends only on its weight.
   *
   * @return the enemy weight
   */
  def maxActionBar: Double = weight.toDouble

  /** @inheritdoc */
  def receiveAction(action: Action, source: Source): Unit =
    action.execute(source, this)
