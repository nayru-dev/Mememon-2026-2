package cl.uchile.dcc
package mememon.exceptions

class InvalidActionException(message: String) extends Exception(message)

class InvalidMovementException(message: String) extends InvalidActionException(message)

class InvalidUnitStateException(message: String) extends InvalidActionException(message)

class InvalidRequirementException(message: String) extends InvalidActionException(message)