package cl.uchile.dcc
package mememon.weapons

/** Represents a lightweight Dagger weapon.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Dagger(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)
