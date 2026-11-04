package cl.uchile.dcc
package mememon.actions

/** Contract for any game entity able to receive actions from a [[Source]]. */
trait Target:
  /** Receives an action performed by a source and applies its effect.
   *
   * @param action the action being received
   * @param source the entity that performed the action
   */
  def receiveAction(action: Action, source: Source): Unit
