package cl.uchile.dcc
package mememon.weapons

/** Represents a heavy Staff designed for high-potency magical attacks.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 * @param magicAttack magical damage capability
 */
class Staff(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack):

  /** @inheritdoc */
  override def canBeEquippedByBlackMage: Boolean = true

  /** @inheritdoc */
  override def canBeEquippedByWhiteMage: Boolean = true
