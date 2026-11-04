package cl.uchile.dcc
package mememon.weapons

/** Abstract base implementation for weapons possessing magical damage.
 *
 * @param name unique name of the weapon
 * @param attack physical attack power
 * @param weight weight threshold of the weapon
 * @param magicAttack magical damage capability
 */
abstract class AbstractMagicWeapon(
                                    name: String,
                                    attack: Int,
                                    weight: Int,
                                    val magicAttack: Int
                                  ) extends AbstractWeapon(name, attack, weight)
  with MagicWeapon
