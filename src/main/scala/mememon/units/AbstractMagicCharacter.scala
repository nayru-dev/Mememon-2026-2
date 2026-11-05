package cl.uchile.dcc
package mememon.units

import mememon.exceptions.InvalidRequirementException

/** Abstract base class for characters that utilize mana resources.
 *
 * @param name identifier name of the magical character
 * @param initialHp initial health points before combat
 * @param defense physical defense value
 * @param weight base weight of the character
 * @param initialMana initial and maximum magical points
 */
abstract class AbstractMagicCharacter(
                                       name: String,
                                       initialHp: Int,
                                       defense: Int,
                                       weight: Int,
                                       initialMana: Int
                                     ) extends AbstractCharacter(name, initialHp, defense, weight)
  with MagicCharacter:
  private val _maxMana: Int = math.max(0, initialMana)
  private var _mana: Int = _maxMana
  private var _spellBoost: Boolean = false

  /** @inheritdoc */
  def mana: Int = _mana

  /** @inheritdoc */
  def maxMana: Int = _maxMana

  /** @inheritdoc */
  def spendMana(amount: Int): Unit =
    if amount > _mana then
      throw new InvalidRequirementException(s"$name needs $amount mana but only has ${_mana}.")
    _mana = _mana - math.max(0, amount)

  /** @inheritdoc */
  override def restoreManaByPercent(percent: Int): Unit =
    val restored = _maxMana * math.max(0, percent) / 100
    _mana = math.min(_maxMana, _mana + restored)

  /** @inheritdoc */
  def hasSpellBoost: Boolean = _spellBoost

  /** @inheritdoc */
  override def boostNextSpell(): Unit =
    _spellBoost = true

  /** @inheritdoc */
  def consumeSpellBoost(): Unit =
    _spellBoost = false

  /** @inheritdoc */
  def magicDamage: Int =
    val power = currentWeapon match
      case Some(weapon) => weapon.magicPower
      case None         => throw new InvalidRequirementException(s"$name has no weapon to cast spells.")
    if _spellBoost then power * 3 / 2 else power
