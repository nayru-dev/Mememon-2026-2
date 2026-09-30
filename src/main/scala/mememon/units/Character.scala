package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon

/** Base contract defining the common attributes and behaviors for any combat unit in the game. */
trait Character:
  /** Display name of the unit. */
  val name: String

  /** Current health points of the unit. */
  def hp: Int

  /** Physical defense points mitigating incoming physical damage. */
  val defense: Int

  /** Base weight of the unit affecting its turn readiness. */
  val weight: Int

  /** Weapon currently equipped by the unit, if any. */
  def currentWeapon: Option[Weapon]

  /** Computes the total physical attack damage dealt by this unit.
   *
   * @return attack power value based on equipped weapon or innate power
   */
  def attackDamage: Int

  /** Reduces the character's health points by an incoming damage value, without dropping below zero.
   *
   * @param amount quantity of damage to apply
   */
  def takeDamage(amount: Int): Unit

  /** Executes a physical attack action targeting an opponent character.
   *
   * @param target the defending unit receiving the attack
   */
  def attack(target: Character): Unit

  /** Calculates the total threshold required to complete the action bar for combat turns.
   *
   * @return maximum action bar threshold
   */
  def maxActionBar: Double

/** Contract for units capable of channeling and spending mana points. */
trait MagicCharacter extends Character:
  /** Current or maximum mana points of the magical unit. */
  val mana: Int

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
  private var _inventory: List[Any] = List.empty

  def hp: Int = _hp

  def currentWeapon: Option[Weapon] = _currentWeapon

  /** Inventory of usable items carried by this character. */
  def inventory: List[Any] = _inventory

  def attackDamage: Int =
    _currentWeapon match
      case Some(w) => w.attack
      case None    => 0

  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  def attack(target: Character): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  def maxActionBar: Double =
    val weaponWeight = _currentWeapon match
      case Some(w) => w.weight.toDouble
      case None    => 0.0
    weight.toDouble + 0.5 * weaponWeight

/** Abstract base class for characters that utilize mana resources.
 *
 * @param name identifier name of the magical character
 * @param initialHp initial health points before combat
 * @param defense physical defense value
 * @param weight base weight of the character
 * @param mana available magical points
 */
abstract class AbstractMagicCharacter(
                                       name: String,
                                       initialHp: Int,
                                       defense: Int,
                                       weight: Int,
                                       val mana: Int
                                     ) extends AbstractCharacter(name, initialHp, defense, weight)
  with MagicCharacter

/** Represents a Knight physical unit specialized in defense.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Knight(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Represents an Archer physical unit specialized in ranged combat.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Archer(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Represents a Thief physical unit specialized in agility.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Thief(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Represents a White Mage unit specializing in restorative magic.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 * @param mana starting mana pool
 */
class WhiteMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana)

/** Represents a Black Mage unit specializing in offensive destructive spells.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 * @param mana starting mana pool
 */
class BlackMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana)

/** Represents a non-playable hostile creature on the battlefield.
 *
 * @param name monster name
 * @param initialHp starting health points
 * @param defense physical defense
 * @param weight monster weight
 * @param attack innate physical attack power
 */
class Enemy(
             val name: String,
             initialHp: Int,
             val defense: Int,
             val weight: Int,
             val attack: Int
           ) extends Character:
  private var _hp: Int = math.max(0, initialHp)

  def currentWeapon: Option[Weapon] = None
  def hp: Int = _hp
  def attackDamage: Int = attack

  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  def attack(target: Character): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  def maxActionBar: Double = weight.toDouble