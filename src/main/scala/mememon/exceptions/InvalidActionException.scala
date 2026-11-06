package cl.uchile.dcc
package mememon.exceptions

/** Base exception thrown when an action cannot be performed.
 *
 * @param message description of why the action failed
 */
class InvalidActionException(message: String) extends Exception(message)
