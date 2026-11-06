package cl.uchile.dcc
package mememon.exceptions

/** Thrown when a unit tries to move to a non-adjacent panel or there is no unit to move.
 *
 * @param message description of why the action failed
 */
class InvalidMovementException(message: String) extends InvalidActionException(message)
