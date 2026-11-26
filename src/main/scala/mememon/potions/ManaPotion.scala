package cl.uchile.dcc
package mememon.potions

import mememon.units.Character

/** Consumable magical potion that restores 30% of the maximum mana points.
 *
 * @param name display name of the mana potion
 */
class ManaPotion(name: String) extends AbstractPotion(name):

  /** @inheritdoc */
  protected def applyEffect(source: Character): Unit =
    source.restoreManaByPercent(30)
