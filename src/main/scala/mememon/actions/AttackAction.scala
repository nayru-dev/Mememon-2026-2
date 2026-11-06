package cl.uchile.dcc
package mememon.actions

import mememon.units.GameUnit

/** Action that lets a unit attack another unit. */
class AttackAction extends AbstractUnitAction("Attack"):

  /** @inheritdoc */
  protected def applyTo(source: GameUnit, target: Target): Unit =
    target.receiveAttack(source)
