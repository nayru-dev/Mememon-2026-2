package cl.uchile.dcc
package mememon.actions

import mememon.exceptions.InvalidActionException
import mememon.units.GameUnit

/** Abstract base for targets: by default, they reject every action.
 *
 * Each subclass overrides only the actions it can receive.
 */
abstract class AbstractTarget extends Target:

  /** @inheritdoc */
  def receiveAttack(source: GameUnit): Unit =
    throw new InvalidActionException(s"${source.name} cannot attack this target.")

  /** @inheritdoc */
  def receiveMove(source: GameUnit): Unit =
    throw new InvalidActionException(s"${source.name} cannot move to this target.")
