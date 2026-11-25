package cl.uchile.dcc
package mememon.factories

import scala.util.Random

/** Abstract base for factories that pick random values, sharing the random generator.
 *
 * @param random generator used to pick the values; a fixed seed makes the results repeatable
 */
abstract class AbstractRandomFactory(random: Random):

  /** Picks a random number inside a range, both limits included.
   *
   * @param min smallest possible value
   * @param max greatest possible value
   * @return a random number between min and max
   */
  protected def randomBetween(min: Int, max: Int): Int =
    min + random.nextInt(max - min + 1)
