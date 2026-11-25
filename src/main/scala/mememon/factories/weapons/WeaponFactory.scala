package cl.uchile.dcc
package mememon.factories.weapons

import mememon.weapons.Weapon

/** Contract for factories that create weapons with random stats. */
trait WeaponFactory:
  /** Creates a new weapon.
   *
   * @return a new weapon with random stats
   */
  def make(): Weapon
