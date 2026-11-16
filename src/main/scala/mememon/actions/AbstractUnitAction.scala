package cl.uchile.dcc
package mememon.actions

import mememon.units.{Archer, BlackMage, Enemy, GameUnit, Knight, Thief, WhiteMage}

/** Abstract base for actions that any unit can perform.
 *
 * @param name display name of the action
 */
abstract class AbstractUnitAction(name: String) extends AbstractAction(name):

  /** @inheritdoc */
  override def executeByKnight(source: Knight, target: Target): Unit = applyTo(source, target)

  /** @inheritdoc */
  override def executeByArcher(source: Archer, target: Target): Unit = applyTo(source, target)

  /** @inheritdoc */
  override def executeByThief(source: Thief, target: Target): Unit = applyTo(source, target)

  /** @inheritdoc */
  override def executeByBlackMage(source: BlackMage, target: Target): Unit = applyTo(source, target)

  /** @inheritdoc */
  override def executeByWhiteMage(source: WhiteMage, target: Target): Unit = applyTo(source, target)

  /** @inheritdoc */
  override def executeByEnemy(source: Enemy, target: Target): Unit = applyTo(source, target)

  /** Applies the effect of this action, asking the target to receive it.
   *
   * @param source the unit performing the action
   * @param target the entity receiving the action
   */
  protected def applyTo(source: GameUnit, target: Target): Unit
