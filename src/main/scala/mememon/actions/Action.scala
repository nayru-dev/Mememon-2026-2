package cl.uchile.dcc
package mememon.actions

trait Source:
  def doAction(action: Action, target: Target): Unit=
    action.execute(this, target)

trait Target:
  def receiveAction(action: Action, source: Source): Unit
  
trait Action:
  val name: String
  def execute(source: Source, target: Target): Unit
