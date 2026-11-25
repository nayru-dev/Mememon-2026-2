package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.weapons.{BowFactory, DaggerFactory, SwordFactory, WeaponFactory}
import mememon.units.{Character, Knight}
import scala.util.Random

/** Factory of knights: hp between 100 and 130, defense between 15 and 25, weight between 12 and 18.
 * Knights get a sword, a dagger or a bow.
 *
 * @param random generator used to pick the stats and resources
 */
class KnightFactory(random: Random) extends AbstractCharacterFactory(random):

  /** @inheritdoc */
  protected def createCharacter(): Character =
    new Knight("Knight", randomBetween(100, 130), randomBetween(15, 25), randomBetween(12, 18))

  /** @inheritdoc */
  protected def weaponFactories: List[WeaponFactory] =
    List(new SwordFactory(random), new DaggerFactory(random), new BowFactory(random))
