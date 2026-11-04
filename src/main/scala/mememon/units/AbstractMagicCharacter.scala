package cl.uchile.dcc
package mememon.units

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
