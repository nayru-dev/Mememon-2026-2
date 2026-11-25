package cl.uchile.dcc
package mememon.factories.weapons

import mememon.weapons.Dagger
import scala.util.Random

/** Factory of daggers: attack between 8 and 15, weight between 2 and 4.
 *
 * @param random generator used to pick the stats
 */
class DaggerFactory(random: Random) extends AbstractWeaponFactory(random):

  /** Creates a new dagger with random stats.
   *
   * @return a new dagger
   */
  override def make(): Dagger =
    new Dagger("Dagger", randomBetween(8, 15), randomBetween(2, 4))
