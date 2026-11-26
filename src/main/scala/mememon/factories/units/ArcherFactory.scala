package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.weapons.{BowFactory, SwordFactory, WandFactory, WeaponFactory}
import mememon.units.{Archer, Character}
import scala.util.Random

/** Factory of archers: hp between 80 and 100, defense between 8 and 14, weight between 8 and 12.
 * Archers get a sword, a bow or a wand.
 *
 * @param random generator used to pick the stats and resources
 */
class ArcherFactory(random: Random) extends AbstractCharacterFactory(random):

  /** @inheritdoc */
  protected def createCharacter(): Character =
    new Archer("Archer", randomBetween(80, 100), randomBetween(8, 14), randomBetween(8, 12))

  /** @inheritdoc */
  protected def weaponFactories: List[WeaponFactory] =
    List(new SwordFactory(random), new BowFactory(random), new WandFactory(random))
