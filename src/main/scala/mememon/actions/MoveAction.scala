package cl.uchile.dcc
package mememon.actions

import mememon.units.GameUnit

/** Action that lets a unit move to an adjacent panel. */
class MoveAction extends AbstractUnitAction("Move"):

  /** @inheritdoc */
  protected def applyTo(source: GameUnit, target: Target): Unit =
    target.receiveMove(source)
