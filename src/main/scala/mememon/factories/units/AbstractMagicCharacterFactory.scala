package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.potions.{MagicAttackPotionFactory, ManaPotionFactory, PotionFactory}
import mememon.factories.weapons.{StaffFactory, WandFactory, WeaponFactory}
import mememon.units.Character
import scala.util.Random

/** Abstract base for mage factories.
 *
 * Mages get hp between 60 and 80, defense between 5 and 10, weight between 4 and 8 and mana
 * between 60 and 100. They only get magic weapons, so they can always cast spells, and they can
 * also get mana and magic attack potions.
 *
 * @param random generator used to pick the stats and resources
 */
abstract class AbstractMagicCharacterFactory(random: Random) extends AbstractCharacterFactory(random):

  /** @inheritdoc */
  protected def createCharacter(): Character =
    createMage(randomBetween(60, 80), randomBetween(5, 10), randomBetween(4, 8), randomBetween(60, 100))

  /** Creates the specific mage with the given stats.
   *
   * @param hp initial health points
   * @param defense physical defense
   * @param weight base weight
   * @param mana initial and maximum mana
   * @return a new mage
   */
  protected def createMage(hp: Int, defense: Int, weight: Int, mana: Int): Character

  /** Mages can only get wands and staffs.
   *
   * @return the wand and staff factories
   */
  protected def weaponFactories: List[WeaponFactory] =
    List(new WandFactory(random), new StaffFactory(random))

  /** Mages can get every kind of potion.
   *
   * @return the four potion factories
   */
  override protected def potionFactories: List[PotionFactory] =
    new ManaPotionFactory :: new MagicAttackPotionFactory :: super.potionFactories
