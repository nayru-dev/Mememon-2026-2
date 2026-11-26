package cl.uchile.dcc
package mememon.actions

import mememon.units.{Character, GameUnit, MagicCharacter}

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

  /** Receives a Thunder spell.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   * @throws mememon.exceptions.InvalidActionException if this target cannot receive Thunder
   */
  def receiveThunder(source: MagicCharacter, damage: Int): Unit

  /** Receives a Meteor spell.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   * @throws mememon.exceptions.InvalidActionException if this target cannot receive Meteor
   */
  def receiveMeteor(source: MagicCharacter, damage: Int): Unit

  /** Receives a Healing spell.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   * @throws mememon.exceptions.InvalidActionException if this target cannot receive Healing
   */
  def receiveHealing(source: MagicCharacter, damage: Int): Unit

  /** Receives a Purification spell.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   * @throws mememon.exceptions.InvalidActionException if this target cannot receive Purification
   */
  def receivePurification(source: MagicCharacter, damage: Int): Unit
