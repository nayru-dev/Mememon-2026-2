package cl.uchile.dcc
package mememon.combat

import mememon.units.GameUnit

/** Contract defining the turn scheduler operations in combat. */
trait TurnScheduler:
  /** Adds a unit to the combat scheduler.
   *
   * @param unit the unit to register
   */
  def addUnit(unit: GameUnit): Unit

  /** Removes a unit from combat and discards its action bar state.
   *
   * @param unit the unit to remove
   */
  def removeUnit(unit: GameUnit): Unit

  /** Returns an immutable list containing all currently registered units.
   *
   * @return list of registered units
   */
  def units: List[GameUnit]

  /** Returns the maximum action bar capacity for a specific unit.
   *
   * @param unit the unit whose threshold is queried
   * @return maximum action bar threshold
   */
  def maxActionBar(unit: GameUnit): Double

  /** Returns the current accumulated action bar value of a unit.
   *
   * @param unit the target unit
   * @return current action bar points
   */
  def currentActionBar(unit: GameUnit): Double

  /** Resets the action bar of a specific unit back to 0.0.
   *
   * @param unit the target unit to reset
   */
  def resetActionBar(unit: GameUnit): Unit

  /** Simultaneously increases the action bar of all units by an arbitrary positive amount.
   *
   * @param amount the positive quantity to add to each action bar
   */
  def increaseActionBars(amount: Double): Unit

  /** Checks whether a specific unit has completed its action bar.
   *
   * @param unit the target unit
   * @return true if the current bar has reached or surpassed the maximum
   */
  def hasCompletedActionBar(unit: GameUnit): Boolean

  /** Returns all units that have completed their action bars, ordered from highest to lowest overflow.
   *
   * @return sorted list of units ready to take action
   */
  def completedUnits: List[GameUnit]

  /** Returns the single unit that is currently entitled to take its turn, if any.
   *
   * @return an Option containing the leading unit, or None if no unit has finished
   */
  def currentTurnUnit: Option[GameUnit]
