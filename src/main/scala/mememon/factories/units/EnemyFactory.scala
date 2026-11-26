package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.AbstractRandomFactory
import mememon.units.Enemy
import scala.util.Random

/** Factory of enemies: hp between 70 and 110, defense between 8 and 15, weight between 10 and 18
 * and attack between 12 and 25. Enemies get no weapons nor potions.
 *
 * @param random generator used to pick the stats
 */
class EnemyFactory(random: Random) extends AbstractRandomFactory(random) with UnitFactory:

  /** Creates a new enemy with random stats.
   *
   * @return a new enemy
   */
  override def make(): Enemy =
    new Enemy("Enemy", randomBetween(70, 110), randomBetween(8, 15), randomBetween(10, 18), randomBetween(12, 25))
