package cl.uchile.dcc
package mememon.actions

import mememon.units.{Archer, BlackMage, Character, Knight, Thief, WhiteMage}

/** Abstract base for actions that any character can perform, but enemies cannot.
 *
 * @param name display name of the action
 */
abstract class AbstractCharacterAction(name: String) extends AbstractAction(name):

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

  /** Applies the effect of this action, asking the target to receive it.
   *
   * @param source the character performing the action
   * @param target the entity receiving the action
   */
  protected def applyTo(source: Character, target: Target): Unit
