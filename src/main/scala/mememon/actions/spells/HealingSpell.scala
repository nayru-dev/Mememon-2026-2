package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.MagicCharacter

/** Light magic spell that heals a character. It costs 15 mana. */
class HealingSpell extends AbstractWhiteSpell("Healing", 15):

  /** @inheritdoc */
  protected def applyEffect(source: MagicCharacter, target: Target, damage: Int): Unit =
    target.receiveHealing(source, damage)
