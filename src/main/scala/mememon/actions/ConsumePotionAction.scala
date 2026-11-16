package cl.uchile.dcc
package mememon.actions

import mememon.units.Character

/** Action that lets a character consume one of the potions in its inventory. */
class ConsumePotionAction extends AbstractUsableAction("Consume Potion"):

  /** @inheritdoc */
  protected def useOn(source: Character, target: Target): Unit =
    target.receiveConsume(source)
