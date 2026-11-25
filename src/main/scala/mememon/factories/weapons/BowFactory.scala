package cl.uchile.dcc
package mememon.factories.weapons

import mememon.weapons.Bow
import scala.util.Random

/** Factory of bows: attack between 15 and 25, weight between 5 and 8.
 *
 * @param random generator used to pick the stats
 */
class BowFactory(random: Random) extends AbstractWeaponFactory(random):

  /** Creates a new bow with random stats.
   *
   * @return a new bow
   */
  override def make(): Bow =
    new Bow("Bow", randomBetween(15, 25), randomBetween(5, 8))
