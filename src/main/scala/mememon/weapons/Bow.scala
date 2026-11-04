package cl.uchile.dcc
package mememon.weapons

/** Represents a Bow used for physical ranged combat.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Bow(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)
