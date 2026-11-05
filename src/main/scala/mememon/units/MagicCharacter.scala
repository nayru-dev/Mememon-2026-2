package cl.uchile.dcc
package mememon.units

/** Contract for units capable of channeling and spending mana points. */
trait MagicCharacter extends Character:
  /** Current mana points of the character.
   *
   * @return the available mana, never below zero
   */
  def mana: Int

  /** Maximum mana points of the character.
   *
   * @return the mana the character started with, never below zero
   */
  def maxMana: Int

  /** Spends mana points.
   *
   * @param amount quantity of mana to spend
   * @throws mememon.exceptions.InvalidRequirementException if there is not enough mana
   */
  def spendMana(amount: Int): Unit

  /** Indicates whether the next spell has increased magical damage.
   *
   * @return true if the next spell is boosted, false otherwise
   */
  def hasSpellBoost: Boolean

  /** Removes the boost of the next spell, because it was already used. */
  def consumeSpellBoost(): Unit

  /** Computes the magical damage of the next spell, including the boost if there is one.
   *
   * @return the magical power of the equipped weapon, increased by 50% if the spell is boosted
   * @throws mememon.exceptions.InvalidRequirementException if the character has no magic weapon
   */
  def magicDamage: Int
