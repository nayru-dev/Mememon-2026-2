package cl.uchile.dcc
package mememon.actions

import mememon.units.Character

/** Action that lets a character equip one of the weapons in its inventory. */
class EquipWeaponAction extends AbstractUsableAction("Equip Weapon"):

  /** @inheritdoc */
  protected def useOn(source: Character, target: Target): Unit =
    target.receiveEquip(source)
