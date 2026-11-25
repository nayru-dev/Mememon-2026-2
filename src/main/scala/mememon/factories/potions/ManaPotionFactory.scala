package cl.uchile.dcc
package mememon.factories.potions

import mememon.potions.ManaPotion

/** Factory of mana potions. */
class ManaPotionFactory extends AbstractPotionFactory("Mana Potion"):

  /** Creates a new mana potion.
   *
   * @return a new mana potion
   */
  override def make(): ManaPotion = new ManaPotion(potionName)
