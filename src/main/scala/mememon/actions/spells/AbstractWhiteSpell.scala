package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.Target
import mememon.units.WhiteMage

/** Abstract base for light magic spells, which only white mages can cast.
 *
 * @param name display name of the spell
 * @param manaCost mana points the spell costs
 */
abstract class AbstractWhiteSpell(name: String, manaCost: Int) extends AbstractSpell(name, manaCost):

  /** @inheritdoc */
  override def executeByWhiteMage(source: WhiteMage, target: Target): Unit =
    cast(source, target)
