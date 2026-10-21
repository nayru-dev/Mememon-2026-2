package cl.uchile.dcc
package mememon.potions

/** Represents any consumable potion item in the game. */
trait Potion:
  /** Unique name identifier of the potion. */
  val name: String

/** Abstract base implementation for potion items.
 *
 * @param name unique name identifier of the potion
 */
abstract class AbstractPotion(val name: String)
  extends Potion

/** Consumable common potion that restores a portion of health points.
 *
 * @param name display name of the healing potion
 */
class HealingPotion(name: String) extends AbstractPotion(name)

/** Consumable common potion that temporarily increases defense points.
 *
 * @param name display name of the defense potion
 */
class DefensePotion(name: String) extends AbstractPotion(name)

/** Consumable magical potion that restores a portion of mana points.
 *
 * @param name display name of the mana potion
 */
class ManaPotion(name: String) extends AbstractPotion(name)

/** Consumable magical potion that enhances magical attack power.
 *
 * @param name display name of the magic attack potion
 */
class MagicAttackPotion(name: String) extends AbstractPotion(name)