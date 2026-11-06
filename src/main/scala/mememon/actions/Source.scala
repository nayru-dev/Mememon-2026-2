package cl.uchile.dcc
package mememon.actions

/** Contract for any game entity able to perform actions over a [[Target]]. */
trait Source:
  /** Display name of the source. */
  val name: String

  /** Returns the actions this source can perform.
   *
   * @return the available actions
   */
  def actions: List[Action]

  /** Searches one of the actions of this source by its name.
   *
   * @param actionName the name of the action
   * @return the action, or None if this source does not have it
   */
  def findAction(actionName: String): Option[Action]

  /** Performs the given action over a target.
   *
   * @param action the action to perform
   * @param target the entity that receives the action
   * @throws mememon.exceptions.InvalidActionException if this source cannot perform the action over the target
   */
  def doAction(action: Action, target: Target): Unit
