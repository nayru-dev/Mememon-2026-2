package cl.uchile.dcc
package mememon.factories.weapons

import mememon.factories.AbstractRandomFactory
import mememon.weapons.Staff
import scala.util.Random

/** Factory of staffs: attack between 8 and 14, weight between 8 and 12, magic attack between 25 and 40.
 *
 * @param random generator used to pick the stats
 */
class StaffFactory(random: Random) extends AbstractRandomFactory(random) with WeaponFactory:

  /** Creates a new staff with random stats.
   *
   * @return a new staff
   */
  override def make(): Staff =
    new Staff("Staff", randomBetween(8, 14), randomBetween(8, 12), randomBetween(25, 40))
