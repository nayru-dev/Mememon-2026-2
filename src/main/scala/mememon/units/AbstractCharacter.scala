package cl.uchile.dcc
package mememon.units

import mememon.items.Usable
import mememon.weapons.Weapon
import mememon.actions.{Action, Source}

/** Abstract implementation encapsulating shared logic across playable characters.
 *
 * @param name identifier name of the character
 * @param initialHp initial health points before combat
 * @param defense physical defense value
 * @param weight base weight of the character
 */
abstract class AbstractCharacter(
                                  val name: String,
                                  initialHp: Int,
                                  val defense: Int,
                                  val weight: Int
                                ) extends Character:
  private var _hp: Int = math.max(0, initialHp)
  private var _currentWeapon: Option[Weapon] = None
  private var _inventory: List[Usable] = List.empty

  /** @inheritdoc */
  def hp: Int = _hp

  /** @inheritdoc */
  def currentWeapon: Option[Weapon] = _currentWeapon

  /** @inheritdoc */
  def inventory: List[Usable] = _inventory

  /** @inheritdoc */
  def attackDamage: Int =
    _currentWeapon match
      case Some(w) => w.attack
      case None    => 0

  /** @inheritdoc */
  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  /** @inheritdoc */
  def attack(target: GameUnit): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  /** Computes the maximum action bar as the character weight plus half the equipped weapon weight.
   *
   * @return weight + 0.5 * weaponWeight
   */
  def maxActionBar: Double =
    val weaponWeight = _currentWeapon match
      case Some(w) => w.weight.toDouble
      case None    => 0.0
    weight.toDouble + 0.5 * weaponWeight

  /** @inheritdoc */
  def receiveAction(action: Action, source: Source): Unit =
    action.execute(source, this)
