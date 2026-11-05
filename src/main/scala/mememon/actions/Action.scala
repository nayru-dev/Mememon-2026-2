package cl.uchile.dcc
package mememon.actions

import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}

/** Contract for any action a [[Source]] can perform over a [[Target]]. */
trait Action:
  /** Display name of the action. */
  val name: String

  /** Executes this action on behalf of a knight.
   *
   * @param source the knight performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if a knight cannot perform this action
   */
  def executeByKnight(source: Knight, target: Target): Unit

  /** Executes this action on behalf of an archer.
   *
   * @param source the archer performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if an archer cannot perform this action
   */
  def executeByArcher(source: Archer, target: Target): Unit

  /** Executes this action on behalf of a thief.
   *
   * @param source the thief performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if a thief cannot perform this action
   */
  def executeByThief(source: Thief, target: Target): Unit

  /** Executes this action on behalf of a black mage.
   *
   * @param source the black mage performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if a black mage cannot perform this action
   */
  def executeByBlackMage(source: BlackMage, target: Target): Unit

  /** Executes this action on behalf of a white mage.
   *
   * @param source the white mage performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if a white mage cannot perform this action
   */
  def executeByWhiteMage(source: WhiteMage, target: Target): Unit

  /** Executes this action on behalf of an enemy.
   *
   * @param source the enemy performing the action
   * @param target the entity receiving the action
   * @throws mememon.exceptions.InvalidActionException if an enemy cannot perform this action
   */
  def executeByEnemy(source: Enemy, target: Target): Unit
