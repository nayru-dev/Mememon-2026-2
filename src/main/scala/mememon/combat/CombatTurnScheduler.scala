package cl.uchile.dcc
package mememon.combat

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import mememon.units.Character

/** Concrete implementation of the combat turn scheduler.
 *
 * Keeps one action bar per registered unit and orders the ready units by their overflow.
 */
class CombatTurnScheduler extends TurnScheduler:
  private val _units: ArrayBuffer[Character] = ArrayBuffer.empty[Character]
  private val _actionBars: mutable.Map[Character, Double] = mutable.Map.empty[Character, Double]

  /** @inheritdoc */
  def addUnit(unit: Character): Unit =
    if !_units.contains(unit) then
      _units += unit
      _actionBars(unit) = 0.0

  /** @inheritdoc */
  def removeUnit(unit: Character): Unit =
    _units -= unit
    _actionBars.remove(unit)

  /** @inheritdoc */
  def units: List[Character] = _units.toList

  /** @inheritdoc */
  def maxActionBar(unit: Character): Double =
    unit.maxActionBar

  /** @inheritdoc */
  def currentActionBar(unit: Character): Double =
    _actionBars.getOrElse(unit, 0.0)

  /** @inheritdoc */
  def resetActionBar(unit: Character): Unit =
    if _units.contains(unit) then
      _actionBars(unit) = 0.0

  /** @inheritdoc */
  def increaseActionBars(amount: Double): Unit =
    if amount > 0.0 then
      for u <- _units do
        val current = _actionBars.getOrElse(u, 0.0)
        _actionBars(u) = current + amount

  /** @inheritdoc */
  def hasCompletedActionBar(unit: Character): Boolean =
    if _units.contains(unit) then
      currentActionBar(unit) >= maxActionBar(unit)
    else
      false

  /** @inheritdoc */
  def completedUnits: List[Character] =
    val ready = _units.filter(hasCompletedActionBar)
    ready.sortBy(u => -(currentActionBar(u) - maxActionBar(u))).toList

  /** @inheritdoc */
  def currentTurnUnit: Option[Character] =
    completedUnits.headOption
