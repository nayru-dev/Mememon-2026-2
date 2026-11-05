package cl.uchile.dcc
package mememon.actions

import mememon.units.GameUnit

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
