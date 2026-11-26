package cl.uchile.dcc
package mememon.potions

import mememon.units.Character

/** Consumable common potion that restores 20% of the maximum health points.
 *
 * @param name display name of the healing potion
 */
class HealingPotion(name: String) extends AbstractPotion(name):

  /** @inheritdoc */
  protected def applyEffect(source: Character): Unit =
    source.heal(source.maxHp * 20 / 100)
