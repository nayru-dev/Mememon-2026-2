package cl.uchile.dcc
package mememon.combat

import mememon.units.Character

/** Contract defining the turn scheduler operations in combat. */
trait TurnScheduler:
  /** Adds a character unit to the combat scheduler.
   *
   * @param unit the character to register
   */
  def addUnit(unit: Character): Unit

  /** Removes a character unit from combat and discards its action bar state.
   *
   * @param unit the character to remove
   */
  def removeUnit(unit: Character): Unit

  /** Returns an immutable list containing all currently registered units.
   *
   * @return list of registered characters
   */
  def units: List[Character]

  /** Returns the maximum action bar capacity for a specific unit.
   *
   * @param unit the character whose threshold is queried
   * @return maximum action bar threshold
   */
  def maxActionBar(unit: Character): Double

  /** Returns the current accumulated action bar value of a unit.
   *
   * @param unit the target character
   * @return current action bar points
   */
  def currentActionBar(unit: Character): Double

  /** Resets the action bar of a specific unit back to 0.0.
   *
   * @param unit the target character to reset
   */
  def resetActionBar(unit: Character): Unit

  /** Simultaneously increases the action bar of all units by an arbitrary positive amount.
   *
   * @param amount the positive quantity to add to each action bar
   */
  def increaseActionBars(amount: Double): Unit

  /** Checks whether a specific unit has completed its action bar.
   *
   * @param unit the target character
   * @return true if the current bar has reached or surpassed the maximum
   */
  def hasCompletedActionBar(unit: Character): Boolean

  /** Returns all units that have completed their action bars, ordered from highest to lowest overflow.
   *
   * @return sorted list of units ready to take action
   */
  def completedUnits: List[Character]

  /** Returns the single unit that is currently entitled to take its turn, if any.
   *
   * @return an Option containing the leading character, or None if no unit has finished
   */
  def currentTurnUnit: Option[Character]
