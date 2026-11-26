package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.weapons.{DaggerFactory, SwordFactory, WeaponFactory}
import mememon.units.{Character, Thief}
import scala.util.Random

/** Factory of thieves: hp between 75 and 95, defense between 8 and 14, weight between 6 and 10.
 * Thieves get a sword or a dagger.
 *
 * @param random generator used to pick the stats and resources
 */
class ThiefFactory(random: Random) extends AbstractCharacterFactory(random):

  /** @inheritdoc */
  protected def createCharacter(): Character =
    new Thief("Thief", randomBetween(75, 95), randomBetween(8, 14), randomBetween(6, 10))

  /** @inheritdoc */
  protected def weaponFactories: List[WeaponFactory] =
    List(new SwordFactory(random), new DaggerFactory(random))
