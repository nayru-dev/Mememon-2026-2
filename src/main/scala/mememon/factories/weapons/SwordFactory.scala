package cl.uchile.dcc
package mememon.factories.weapons

import mememon.factories.AbstractRandomFactory
import mememon.weapons.Sword
import scala.util.Random

/** Factory of swords: attack between 25 and 40, weight between 8 and 12.
 *
 * @param random generator used to pick the stats
 */
class SwordFactory(random: Random) extends AbstractRandomFactory(random) with WeaponFactory:

  /** Creates a new sword with random stats.
   *
   * @return a new sword
   */
  override def make(): Sword =
    new Sword("Sword", randomBetween(25, 40), randomBetween(8, 12))
