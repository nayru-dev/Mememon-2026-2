package cl.uchile.dcc
package mememon.weapons

import mememon.units.Character

/** Abstract base implementation for physical weapons.
 *
 * @param name unique name of the weapon
 * @param attack physical attack power
 * @param weight weight threshold of the weapon
 */
abstract class AbstractWeapon(val name: String, val attack: Int, val weight: Int)
  extends Weapon:
  private var _owner: Option[Character] = None

  /** @inheritdoc */
  def owner: Option[Character] = _owner

  /** @inheritdoc */
  def setOwner(character: Character): Unit =
    _owner = Some(character)

  /** @inheritdoc */
  def canBeEquippedByKnight: Boolean = false

  /** @inheritdoc */
  def canBeEquippedByArcher: Boolean = false

  /** @inheritdoc */
  def canBeEquippedByThief: Boolean = false

  /** @inheritdoc */
  def canBeEquippedByBlackMage: Boolean = false

  /** @inheritdoc */
  def canBeEquippedByWhiteMage: Boolean = false
