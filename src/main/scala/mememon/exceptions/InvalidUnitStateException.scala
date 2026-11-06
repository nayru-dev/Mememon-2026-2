package cl.uchile.dcc
package mememon.exceptions

/** Thrown when a unit is in a state that does not allow the action (e.g. defeated).
 *
 * @param message description of why the action failed
 */
class InvalidUnitStateException(message: String) extends InvalidActionException(message)
