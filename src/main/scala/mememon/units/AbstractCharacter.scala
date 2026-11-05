package cl.uchile.dcc
package mememon.units

import mememon.actions.{Action, ConsumePotionAction, EquipWeaponAction}
import mememon.exceptions.InvalidRequirementException
import mememon.items.Usable
import mememon.weapons.Weapon

/** Abstract implementation encapsulating shared logic across playable characters.
 *
 * @param name identifier name of the character
 * @param initialHp initial health points before combat
 * @param defense physical defense value
 * @param weight base weight of the character
 */
abstract class AbstractCharacter(
                                  name: String,
                                  initialHp: Int,
                                  defense: Int,
                                  weight: Int
                                ) extends AbstractGameUnit(name, initialHp, defense, weight)
  with Character:
  private var _currentWeapon: Option[Weapon] = None
  private var _inventory: List[Usable] = List.empty
  private val _equipAction: EquipWeaponAction = new EquipWeaponAction
  private val _consumeAction: ConsumePotionAction = new ConsumePotionAction

  /** @inheritdoc */
  override def actions: List[Action] =
    _equipAction :: _consumeAction :: super.actions

  /** @inheritdoc */
  def currentWeapon: Option[Weapon] = _currentWeapon

  /** @inheritdoc */
  def inventory: List[Usable] = _inventory

  /** @inheritdoc */
  def addToInventory(item: Usable): Unit =
    _inventory = item :: _inventory
    item.offerTo(_equipAction, _consumeAction)

  /** @inheritdoc */
  def removeFromInventory(item: Usable): Unit =
    _inventory = _inventory.filter(current => current != item)
    _equipAction.removePossibleItem(item)
    _consumeAction.removePossibleItem(item)

  /** Heals 20% of the maximum health points plus a quarter of the magical damage.
   *
   * @param source the mage casting the spell
   * @param damage the magical damage of the spell
   */
  override def receiveHealing(source: MagicCharacter, damage: Int): Unit =
    heal(maxHp * 20 / 100 + damage / 4)

  /** @inheritdoc */
  def restoreManaByPercent(percent: Int): Unit =
    throw new InvalidRequirementException(s"$name is not a magic character, so it has no mana.")

  /** @inheritdoc */
  def boostNextSpell(): Unit =
    throw new InvalidRequirementException(s"$name is not a magic character, so it casts no spells.")

  /** @inheritdoc */
  def equip(weapon: Weapon): Unit =
    if !canEquip(weapon) then
      throw new InvalidRequirementException(s"$name cannot equip ${weapon.name}.")
    _currentWeapon = Some(weapon)
    weapon.setOwner(this)

  /** Asks the weapon whether this kind of character can equip it.
   *
   * @param weapon the weapon to check
   * @return true if this character can equip the weapon, false otherwise
   */
  protected def canEquip(weapon: Weapon): Boolean

  /** @inheritdoc */
  def attackDamage: Int =
    _currentWeapon match
      case Some(w) => w.attack
      case None    => 0

  /** Computes the maximum action bar as the character weight plus half the equipped weapon weight.
   *
   * @return weight + 0.5 * weaponWeight
   */
  def maxActionBar: Double =
    val weaponWeight = _currentWeapon match
      case Some(w) => w.weight.toDouble
      case None    => 0.0
    weight.toDouble + 0.5 * weaponWeight
