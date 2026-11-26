package cl.uchile.dcc
package mememon.factories.units

import mememon.units.{Character, WhiteMage}
import scala.util.Random

/** Factory of white mages.
 *
 * @param random generator used to pick the stats and resources
 */
class WhiteMageFactory(random: Random) extends AbstractMagicCharacterFactory(random):

  /** @inheritdoc */
  protected def createMage(hp: Int, defense: Int, weight: Int, mana: Int): Character =
    new WhiteMage("White Mage", hp, defense, weight, mana)
