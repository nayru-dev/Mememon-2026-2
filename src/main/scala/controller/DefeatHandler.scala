package cl.uchile.dcc
package controller

import mememon.events.UnitDefeated
import mememon.observers.{Observer, Subject}

/** Observer that forwards unit defeats to the game controller.
 *
 * @param controller the controller that reacts to the defeats
 */
class DefeatHandler(controller: GameController) extends Observer[UnitDefeated]:
  /** @inheritdoc */
  override def update(subject: Subject[UnitDefeated], response: UnitDefeated): Unit =
    controller.onUnitDefeated(subject, response)
