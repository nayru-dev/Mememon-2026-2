package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.MagicCharacter

/** Dark magic spell that damages every unit on a panel by the magical damage. It costs 50 mana. */
class MeteorSpell extends AbstractBlackSpell("Meteor", 50):

  /** @inheritdoc */
  protected def applyEffect(source: MagicCharacter, target: Target, damage: Int): Unit =
    target.receiveMeteor(source, damage)
