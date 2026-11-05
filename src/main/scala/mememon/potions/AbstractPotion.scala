package cl.uchile.dcc
package mememon.potions

import mememon.actions.{AbstractTarget, ConsumePotionAction, EquipWeaponAction}
import mememon.units.Character

/** Abstract base implementation for potion items.
 *
 * @param name unique name identifier of the potion
 */
abstract class AbstractPotion(val name: String)
  extends AbstractTarget with Potion:

  /** @inheritdoc */
  def offerTo(equip: EquipWeaponAction, consume: ConsumePotionAction): Unit =
    consume.addPossibleItem(this)

  /** @inheritdoc */
  override def receiveConsume(source: Character): Unit =
    applyEffect(source)
    source.removeFromInventory(this)

  /** Applies the effect of this potion to the character consuming it.
   *
   * @param source the character consuming the potion
   */
  protected def applyEffect(source: Character): Unit
