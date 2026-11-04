package cl.uchile.dcc
package mememon.weapons

/** Abstract base implementation for physical weapons.
 *
 * @param name unique name of the weapon
 * @param attack physical attack power
 * @param weight weight threshold of the weapon
 */
abstract class AbstractWeapon(val name: String, val attack: Int, val weight: Int)
  extends Weapon
