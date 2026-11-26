package cl.uchile.dcc
package mememon.factories.potions

import mememon.potions.Potion

/** Contract for factories that create potions. */
trait PotionFactory:
  /** Creates a new potion.
   *
   * @return a new potion
   */
  def make(): Potion
