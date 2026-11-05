package cl.uchile.dcc
package mememon.actions.spells

import mememon.actions.{AbstractAction, Target}
import mememon.exceptions.InvalidRequirementException
import mememon.units.MagicCharacter

/** Abstract base for spells, which spend mana and use the magical damage of the caster.
 *
 * @param name display name of the spell
 * @param manaCost mana points the spell costs
 */
abstract class AbstractSpell(name: String, val manaCost: Int) extends AbstractAction(name):

  /** Validates the requirements, applies the effect and only then pays for the spell.
   *
   * @param source the mage casting the spell
   * @param target the entity receiving the spell
   * @throws mememon.exceptions.InvalidRequirementException if there is no mana or no magic weapon
   */
  protected def cast(source: MagicCharacter, target: Target): Unit =
    if source.mana < manaCost then
      throw new InvalidRequirementException(
        s"${source.name} needs $manaCost mana to cast $name but only has ${source.mana}."
      )
    applyEffect(source, target, source.magicDamage)
    source.spendMana(manaCost)
    source.consumeSpellBoost()

  /** Applies the effect of this spell, asking the target to receive it.
   *
   * @param source the mage casting the spell
   * @param target the entity receiving the spell
   * @param damage the magical damage of the spell
   */
  protected def applyEffect(source: MagicCharacter, target: Target, damage: Int): Unit
