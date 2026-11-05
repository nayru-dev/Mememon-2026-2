package cl.uchile.dcc
package mememon.actions

import mememon.units.{Character, GameUnit}

/** Contract for any game entity able to receive actions from a [[Source]]. */
trait Target:
  /** Receives an attack from a unit.
   *
   * @param source the attacking unit
   * @throws mememon.exceptions.InvalidActionException if this target cannot be attacked
   */
  def receiveAttack(source: GameUnit): Unit

  /** Receives a unit that wants to move here.
   *
   * @param source the moving unit
   * @throws mememon.exceptions.InvalidActionException if a unit cannot move to this target
   */
  def receiveMove(source: GameUnit): Unit

  /** Receives a character that wants to equip this target.
   *
   * @param source the character equipping the target
   * @throws mememon.exceptions.InvalidActionException if this target cannot be equipped
   */
  def receiveEquip(source: Character): Unit

  /** Receives a character that wants to consume this target.
   *
   * @param source the character consuming the target
   * @throws mememon.exceptions.InvalidActionException if this target cannot be consumed
   */
  def receiveConsume(source: Character): Unit
