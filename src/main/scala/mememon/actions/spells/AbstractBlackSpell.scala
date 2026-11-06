package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.BlackMage

/** Abstract base for dark magic spells, which only black mages can cast.
 *
 * @param name display name of the spell
 * @param manaCost mana points the spell costs
 */
abstract class AbstractBlackSpell(name: String, manaCost: Int) extends AbstractSpell(name, manaCost):

  /** @inheritdoc */
  override def executeByBlackMage(source: BlackMage, target: Target): Unit =
    cast(source, target)
