package cl.uchile.dcc
package mememon.combat

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import mememon.units.Character

/** Contract defining the turn scheduler operations in combat. */
trait TurnScheduler:
  /** Adds a character unit to the combat scheduler. */
  def addUnit(unit: Character): Unit

  /** Removes a character unit from combat and discards its action bar state. */
  def removeUnit(unit: Character): Unit

  /** Returns an immutable list containing all currently registered units. */
  def units: List[Character]

  /** Returns the maximum action bar capacity for a specific unit. */
  def maxActionBar(unit: Character): Double

  /** Returns the current accumulated action bar value of a unit. */
  def currentActionBar(unit: Character): Double

  /** Resets the action bar of a specific unit back to 0.0. */
  def resetActionBar(unit: Character): Unit

  /** Simultaneously increases the action bar of all units by an arbitrary positive amount. */
  def increaseActionBars(amount: Double): Unit

  /** Checks whether a specific unit has completed its action bar. */
  def hasCompletedActionBar(unit: Character): Boolean

  /** Returns all units that have completed their action bars, ordered from highest to lowest overflow. */
  def completedUnits: List[Character]

  /** Returns the single unit that is currently entitled to take its turn, if any. */
  def currentTurnUnit: Option[Character]

/** Concrete implementation of the combat turn scheduler. */
class CombatTurnScheduler extends TurnScheduler:
  private val _units: ArrayBuffer[Character] = ArrayBuffer.empty[Character]
  private val _actionBars: mutable.Map[Character, Double] = mutable.Map.empty[Character, Double]

  def addUnit(unit: Character): Unit =
    if !_units.contains(unit) then
      _units += unit
      _actionBars(unit) = 0.0

  def removeUnit(unit: Character): Unit =
    _units -= unit
    _actionBars.remove(unit)

  def units: List[Character] = _units.toList

  def maxActionBar(unit: Character): Double =
    unit.maxActionBar

  def currentActionBar(unit: Character): Double =
    _actionBars.getOrElse(unit, 0.0)

  def resetActionBar(unit: Character): Unit =
    if _units.contains(unit) then
      _actionBars(unit) = 0.0

  def increaseActionBars(amount: Double): Unit =
    if amount > 0.0 then
      for u <- _units do
        val current = _actionBars.getOrElse(u, 0.0)
        _actionBars(u) = current + amount

  def hasCompletedActionBar(unit: Character): Boolean =
    if _units.contains(unit) then
      currentActionBar(unit) >= maxActionBar(unit)
    else
      false

  def completedUnits: List[Character] =
    val ready = _units.filter(hasCompletedActionBar)
    ready.sortBy(u => -(currentActionBar(u) - maxActionBar(u))).toList

  def currentTurnUnit: Option[Character] =
    completedUnits.headOption