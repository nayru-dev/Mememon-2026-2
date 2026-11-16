package cl.uchile.dcc
package controller

import mememon.actions.{Action, Target}
import mememon.combat.{CombatTurnScheduler, TurnScheduler}
import mememon.events.UnitDefeated
import mememon.exceptions.InvalidActionException
import mememon.observers.Subject
import mememon.players.Player
import mememon.units.GameUnit
import scala.collection.mutable.ArrayBuffer

/** Main controller of the game.
 *
 * Connects the model entities, manages the turns and executes the actions.
 * Through a [[DefeatHandler]] it observes the units of every team, so it is notified when a unit
 * is defeated and can end the game as soon as a whole team has fallen.
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
  private var _players: ArrayBuffer[Player] = ArrayBuffer.empty[Player]
  private var _gameOver: Boolean = false
  private val _defeatHandler: DefeatHandler = new DefeatHandler(this)

  /** Registers a team: its units join the combat and the controller observes each of them.
   *
   * @param player the team to register
   */
  def addPlayer(player: Player): Unit =
    _players += player
    for unit <- player.units do
      addUnit(unit)
      unit.defeatPublisher.attach(_defeatHandler)

  /** Returns the registered teams.
   *
   * @return the teams of the current game
   */
  def players: List[Player] = _players.toList

  /** Reacts to the defeat of an observed unit, ending the game if a whole team has fallen.
   *
   * @param subject the publisher that announced the defeat
   * @param response the defeat event
   */
  def onUnitDefeated(subject: Subject[UnitDefeated], response: UnitDefeated): Unit =
    if _players.filter(player => player.isDefeated).nonEmpty then
      _gameOver = true

  /** Tells whether a whole team has been defeated.
   *
   * @return true if the game has finished, false otherwise
   */
  def isGameOver: Boolean = _gameOver

  /** Returns the message announcing the end of the game.
   *
   * @return the winning team, or that no team survived; None while the game goes on
   */
  def gameOverMessage: Option[String] =
    if !_gameOver then None
    else
      val survivors = _players.filter(player => !player.isDefeated)
      if survivors.isEmpty then Some("Game over: no team survived.")
      else Some(s"Game over: ${survivors.head.name} wins.")

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
   * Defeated units are removed from combat first. Once the game is over, no unit plays.
   */
  def nextTurn(): Unit =
    removeDefeatedUnits()
    if _gameOver then
      _currentUnit = None
    else
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
   * Once the game is over, every action fails with the [[gameOverMessage]].
   *
   * @param action the action to perform
   * @param target the entity that receives the action
   * @return true if the action succeeded, false otherwise
   */
  def executeAction(action: Action, target: Target): Boolean =
    if _gameOver then
      _lastError = gameOverMessage
      false
    else
      performAction(action, target)

  /** Resets the game: removes all units, teams, the current turn, the last error and the game over state. */
  def reset(): Unit =
    for
      player <- _players
      unit <- player.units
    do unit.defeatPublisher.detach(_defeatHandler)
    _scheduler = new CombatTurnScheduler
    _currentUnit = None
    _lastError = None
    _players = ArrayBuffer.empty[Player]
    _gameOver = false

  private def performAction(action: Action, target: Target): Boolean =
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

  private def endTurn(unit: GameUnit): Unit =
    _scheduler.resetActionBar(unit)
    nextTurn()

  private def removeDefeatedUnits(): Unit =
    for unit <- _scheduler.units do
      if unit.hp <= 0 then
        _scheduler.removeUnit(unit)
        unit.currentPanel match
          case Some(panel) => panel.removeUnit(unit)
          case None        => ()
