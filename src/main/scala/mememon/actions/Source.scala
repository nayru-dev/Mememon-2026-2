package cl.uchile.dcc
package mememon.actions

/** Contract for any game entity able to perform actions over a [[Target]]. */
trait Source:
  /** Performs the given action over a target.
   *
   * @param action the action to perform
   * @param target the entity that receives the action
   */
  def doAction(action: Action, target: Target): Unit =
    action.execute(this, target)
