package cl.uchile.dcc
package mememon.factories.potions

import mememon.potions.MagicAttackPotion

/** Factory of magic attack potions. */
class MagicAttackPotionFactory extends AbstractPotionFactory("Magic Attack Potion"):

  /** Creates a new magic attack potion.
   *
   * @return a new magic attack potion
   */
  override def make(): MagicAttackPotion = new MagicAttackPotion(potionName)
