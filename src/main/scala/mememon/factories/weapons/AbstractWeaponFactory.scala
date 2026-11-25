package cl.uchile.dcc
package mememon.factories.weapons

import scala.util.Random

/** Abstract base for weapon factories, sharing the random generator used for the stats.
 *
 * @param random generator used to pick the stats; a fixed seed makes the results repeatable
 */
abstract class AbstractWeaponFactory(random: Random) extends WeaponFactory:

  /** Picks a random number inside a range, both limits included.
   *
   * @param min smallest possible value
   * @param max greatest possible value
   * @return a random number between min and max
   */
  protected def randomBetween(min: Int, max: Int): Int =
    min + random.nextInt(max - min + 1)
