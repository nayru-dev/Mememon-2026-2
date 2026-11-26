package cl.uchile.dcc
package mememon.exceptions

/** Thrown when an action requirement is not met (e.g. missing weapon or mana).
 *
 * @param message description of why the action failed
 */
class InvalidRequirementException(message: String) extends InvalidActionException(message)
