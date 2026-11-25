package cl.uchile.dcc
package mememon.factories.units

import mememon.factories.AbstractRandomFactory
import mememon.factories.potions.{DefensePotionFactory, HealingPotionFactory, PotionFactory}
import mememon.factories.weapons.WeaponFactory
import mememon.units.Character
import scala.util.Random

/** Abstract base for character factories.
 *
 * It is a client of the weapon and potion factories: every created character gets one random
 * weapon (stored in its inventory and equipped) and between one and three random potions.
 *
 * @param random generator used to pick the stats and resources
 */
abstract class AbstractCharacterFactory(random: Random)
  extends AbstractRandomFactory(random) with UnitFactory:

  /** Creates a new character with a random weapon equipped and random potions in its inventory.
   *
   * @return a new character ready for combat
   */
  override def make(): Character =
    val character = createCharacter()
    val weapon = weaponFactories(randomBetween(0, weaponFactories.length - 1)).make()
    character.addToInventory(weapon)
    character.equip(weapon)
    for i <- 1 to randomBetween(1, 3) do
      character.addToInventory(potionFactories(randomBetween(0, potionFactories.length - 1)).make())
    character

  /** Creates the character with random stats, still without weapon or potions.
   *
   * @return a new character
   */
  protected def createCharacter(): Character

  /** Returns the factories of the weapons this kind of character can equip.
   *
   * @return the weapon factories to choose from
   */
  protected def weaponFactories: List[WeaponFactory]

  /** Returns the factories of the potions this kind of character can use.
   * Non-magic characters only get healing and defense potions.
   *
   * @return the potion factories to choose from
   */
  protected def potionFactories: List[PotionFactory] =
    List(new HealingPotionFactory, new DefensePotionFactory)
