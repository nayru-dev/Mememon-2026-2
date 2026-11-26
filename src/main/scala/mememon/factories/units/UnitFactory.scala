package cl.uchile.dcc
package mememon.factories.units

import mememon.units.GameUnit

/** Contract for factories that create the units of a team with random stats and resources. */
trait UnitFactory:
  /** Creates a new unit.
   *
   * @return a new unit ready for combat
   */
  def make(): GameUnit
