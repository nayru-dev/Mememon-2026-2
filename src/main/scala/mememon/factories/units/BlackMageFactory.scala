package cl.uchile.dcc
package mememon.factories.units

import mememon.units.{BlackMage, Character}
import scala.util.Random

/** Factory of black mages.
 *
 * @param random generator used to pick the stats and resources
 */
class BlackMageFactory(random: Random) extends AbstractMagicCharacterFactory(random):

  /** @inheritdoc */
  protected def createMage(hp: Int, defense: Int, weight: Int, mana: Int): Character =
    new BlackMage("Black Mage", hp, defense, weight, mana)
