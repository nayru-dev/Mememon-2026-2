package cl.uchile.dcc
package mememon.factories.potions

import mememon.potions.DefensePotion

/** Factory of defense potions. */
class DefensePotionFactory extends AbstractPotionFactory("Defense Potion"):

  /** Creates a new defense potion.
   *
   * @return a new defense potion
   */
  override def make(): DefensePotion = new DefensePotion(potionName)
