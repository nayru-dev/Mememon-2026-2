package cl.uchile.dcc
package mememon.players

import mememon.units.Character

/** Represents a player controlling a squad of combat units.
 *
 * @param name identifier name of the player
 * @param units list of character units assigned to this player's team
 */
class Player(val name: String, val units: List[Character]):

  /** Checks whether all characters in the player's team have been defeated.
   *
   * @return true if every unit in the team has 0 or less health points, false otherwise
   */
  def isDefeated: Boolean =
    units.forall(unit => unit.hp <= 0)