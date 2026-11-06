package cl.uchile.dcc
package mememon.potions

import mememon.units.Character

/** Consumable common potion that increases the defense points by 15%.
 *
 * @param name display name of the defense potion
 */
class DefensePotion(name: String) extends AbstractPotion(name):

  /** @inheritdoc */
  protected def applyEffect(source: Character): Unit =
    source.increaseDefense(source.defense * 15 / 100)
