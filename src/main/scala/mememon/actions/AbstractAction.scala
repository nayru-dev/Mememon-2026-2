package cl.uchile.dcc
package mememon.actions

import mememon.exceptions.InvalidActionException
import mememon.units.{Archer, BlackMage, Enemy, GameUnit, Knight, Thief, WhiteMage}

/** Abstract base for actions: by default, no source is allowed to perform them.
 *
 * Each subclass overrides only the sources that can perform it.
 *
 * @param name display name of the action
 */
abstract class AbstractAction(val name: String) extends Action:

  /** @inheritdoc */
  def executeByKnight(source: Knight, target: Target): Unit = reject(source)

  /** @inheritdoc */
  def executeByArcher(source: Archer, target: Target): Unit = reject(source)

  /** @inheritdoc */
  def executeByThief(source: Thief, target: Target): Unit = reject(source)

  /** @inheritdoc */
  def executeByBlackMage(source: BlackMage, target: Target): Unit = reject(source)

  /** @inheritdoc */
  def executeByWhiteMage(source: WhiteMage, target: Target): Unit = reject(source)

  /** @inheritdoc */
  def executeByEnemy(source: Enemy, target: Target): Unit = reject(source)

  private def reject(source: GameUnit): Unit =
    throw new InvalidActionException(s"${source.name} cannot perform $name.")
