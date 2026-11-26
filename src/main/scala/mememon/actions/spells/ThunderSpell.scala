package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.MagicCharacter

/** Dark magic spell that reduces the health of a unit by the magical damage. It costs 20 mana. */
class ThunderSpell extends AbstractBlackSpell("Thunder", 20):

  /** @inheritdoc */
  protected def applyEffect(source: MagicCharacter, target: Target, damage: Int): Unit =
    target.receiveThunder(source, damage)
