package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.MagicCharacter

/** Light magic spell that curses an enemy, reducing its health. It costs 25 mana. */
class PurificationSpell extends AbstractWhiteSpell("Purification", 25):

  /** @inheritdoc */
  protected def applyEffect(source: MagicCharacter, target: Target, damage: Int): Unit =
    target.receivePurification(source, damage)
