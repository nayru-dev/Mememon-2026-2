package cl.uchile.dcc
package mememon.potions

import mememon.units.Character

/** Consumable magical potion that increases the magical damage of the next spell by 50%.
 *
 * @param name display name of the magic attack potion
 */
class MagicAttackPotion(name: String) extends AbstractPotion(name):

  /** @inheritdoc */
  protected def applyEffect(source: Character): Unit =
    source.boostNextSpell()
