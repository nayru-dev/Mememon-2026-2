package cl.uchile.dcc
package mememon.units

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

  /** @inheritdoc */
  def currentWeapon: Option[Weapon] = _currentWeapon

  /** @inheritdoc */
  def inventory: List[Usable] = _inventory

  /** @inheritdoc */
  def addToInventory(item: Usable): Unit =
    _inventory = item :: _inventory

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
