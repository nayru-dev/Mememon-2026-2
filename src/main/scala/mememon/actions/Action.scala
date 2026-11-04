package cl.uchile.dcc
package mememon.actions

/** Contract for any action a [[Source]] can perform over a [[Target]]. */
trait Action:
  /** Display name of the action. */
  val name: String

  /** Executes the effect of this action.
   *
   * @param source the entity performing the action
   * @param target the entity receiving the action
   */
  def execute(source: Source, target: Target): Unit
