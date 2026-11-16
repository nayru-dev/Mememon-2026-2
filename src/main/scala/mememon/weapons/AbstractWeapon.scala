package cl.uchile.dcc
package mememon.weapons

import mememon.actions.{AbstractTarget, ConsumePotionAction, EquipWeaponAction}
import mememon.exceptions.InvalidRequirementException
import mememon.units.Character

/** Abstract base implementation for physical weapons.
 *
 * @param name display name of the weapon
 * @param attack physical attack power
 * @param weight weight of the weapon
 */
abstract class AbstractWeapon(val name: String, val attack: Int, val weight: Int)
  extends AbstractTarget with Weapon:
  private var _owner: Option[Character] = None

  /** @inheritdoc */
  def magicPower: Int =
    throw new InvalidRequirementException(s"$name is not a magic weapon.")

  /** @inheritdoc */
  def offerTo(equip: EquipWeaponAction, consume: ConsumePotionAction): Unit =
    equip.addPossibleItem(this)

  /** @inheritdoc */
  override def receiveEquip(source: Character): Unit =
    source.equip(this)

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
