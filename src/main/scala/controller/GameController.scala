package cl.uchile.dcc
package controller

import mememon.actions.{Action, Target}
import mememon.combat.{CombatTurnScheduler, TurnScheduler}
import mememon.exceptions.InvalidActionException
import mememon.units.GameUnit

/** Main controller of the game.
 *
 * Connects the model entities, manages the turns and executes the actions.
 *
 * @param barIncrement positive amount added to every action bar until a unit completes its own
 * @throws IllegalArgumentException if the increment is not positive
 */
class GameController(barIncrement: Double = 1.0):
  if barIncrement <= 0.0 then
    throw new IllegalArgumentException(s"The action bar increment must be positive: $barIncrement")

  private var _scheduler: TurnScheduler = new CombatTurnScheduler
  private var _currentUnit: Option[GameUnit] = None
  private var _lastError: Option[String] = None

  /** Registers a unit in the current game.
   *
   * @param unit the unit to register
   */
  def addUnit(unit: GameUnit): Unit =
    _scheduler.addUnit(unit)

  /** Returns the units that are still in combat.
   *
   * @return the registered units
   */
  def units: List[GameUnit] = _scheduler.units

  /** Returns the unit playing the current turn.
   *
   * @return the current unit, or None if no turn has started
   */
  def currentUnit: Option[GameUnit] = _currentUnit

  /** Returns the reason why the last action failed.
   *
   * @return the error message, or None if the last action did not fail
   */
  def lastError: Option[String] = _lastError

  /** Decides which unit plays next, filling the action bars until one is complete.
   *
   * Defeated units are removed from combat first.
   */
  def nextTurn(): Unit =
    removeDefeatedUnits()
    if _scheduler.units.nonEmpty then
      while _scheduler.currentTurnUnit.isEmpty do
        _scheduler.increaseActionBars(barIncrement)
    _currentUnit = _scheduler.currentTurnUnit

  /** Returns the actions a unit can perform.
   *
   * @param unit the unit to consult
   * @return the available actions of the unit
   */
  def availableActions(unit: GameUnit): List[Action] = unit.actions

  /** Makes the current unit perform an action. If it succeeds, the turn ends.
   *
   * If it fails, the turn does not change and the reason is available in [[lastError]].
   *
   * @param action the action to perform
   * @param target the entity that receives the action
   * @return true if the action succeeded, false otherwise
   */
  def executeAction(action: Action, target: Target): Boolean =
    _currentUnit match
      case None =>
        _lastError = Some("There is no unit playing its turn.")
        false
      case Some(unit) =>
        try
          unit.doAction(action, target)
          _lastError = None
          endTurn(unit)
          true
        catch
          case e: InvalidActionException =>
            _lastError = Some(e.getMessage)
            false

  /** Resets the game: removes all units, the current turn and the last error. */
  def reset(): Unit =
    _scheduler = new CombatTurnScheduler
    _currentUnit = None
    _lastError = None

  private def endTurn(unit: GameUnit): Unit =
    _scheduler.resetActionBar(unit)
    nextTurn()

  private def removeDefeatedUnits(): Unit =
    for unit <- _scheduler.units do
      if unit.hp <= 0 then _scheduler.removeUnit(unit)
