package cl.uchile.dcc
package mememon.items

import mememon.actions.{ConsumePotionAction, EquipWeaponAction, Target}

/** Contract for any object a character can store in its inventory and use. */
trait Usable extends Target:
  /** Display name of the usable object. */
  val name: String

  /** Registers this object in the action that can use it.
   *
   * @param equip the action that uses weapons
   * @param consume the action that uses potions
   */
  def offerTo(equip: EquipWeaponAction, consume: ConsumePotionAction): Unit
