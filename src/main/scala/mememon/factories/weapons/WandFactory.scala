package cl.uchile.dcc
package mememon.factories.weapons

import mememon.factories.AbstractRandomFactory
import mememon.weapons.Wand
import scala.util.Random

/** Factory of wands: attack between 2 and 6, weight between 1 and 3, magic attack between 20 and 35.
 *
 * @param random generator used to pick the stats
 */
class WandFactory(random: Random) extends AbstractRandomFactory(random) with WeaponFactory:

  /** Creates a new wand with random stats.
   *
   * @return a new wand
   */
  override def make(): Wand =
    new Wand("Wand", randomBetween(2, 6), randomBetween(1, 3), randomBetween(20, 35))
