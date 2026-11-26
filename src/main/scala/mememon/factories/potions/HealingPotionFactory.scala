package cl.uchile.dcc
package mememon.factories.potions

import mememon.potions.HealingPotion

/** Factory of healing potions. */
class HealingPotionFactory extends AbstractPotionFactory("Healing Potion"):

  /** Creates a new healing potion.
   *
   * @return a new healing potion
   */
  override def make(): HealingPotion = new HealingPotion(potionName)
