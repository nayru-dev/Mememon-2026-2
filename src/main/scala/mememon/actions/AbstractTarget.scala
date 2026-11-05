package cl.uchile.dcc
package mememon.actions

import mememon.exceptions.InvalidActionException
import mememon.units.{Character, GameUnit, MagicCharacter}

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

  /** @inheritdoc */
  def receiveEquip(source: Character): Unit =
    throw new InvalidActionException(s"${source.name} cannot equip this target.")

  /** @inheritdoc */
  def receiveConsume(source: Character): Unit =
    throw new InvalidActionException(s"${source.name} cannot consume this target.")

  /** @inheritdoc */
  def receiveThunder(source: MagicCharacter, damage: Int): Unit =
    throw new InvalidActionException(s"${source.name} cannot cast Thunder on this target.")

  /** @inheritdoc */
  def receiveMeteor(source: MagicCharacter, damage: Int): Unit =
    throw new InvalidActionException(s"${source.name} cannot cast Meteor on this target.")
