package cl.uchile.dcc
package mememon.weapons

/** Represents a standard Sword used for physical melee attacks.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Sword(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight):

  /** @inheritdoc */
  override def canBeEquippedByKnight: Boolean = true

  /** @inheritdoc */
  override def canBeEquippedByArcher: Boolean = true

  /** @inheritdoc */
  override def canBeEquippedByThief: Boolean = true
