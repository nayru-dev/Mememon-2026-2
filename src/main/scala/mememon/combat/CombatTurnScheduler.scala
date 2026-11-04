package cl.uchile.dcc
package mememon.combat

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import mememon.units.GameUnit

/** Concrete implementation of the combat turn scheduler.
 *
 * Keeps one action bar per registered unit and orders the ready units by their overflow.
 */
class CombatTurnScheduler extends TurnScheduler:
  private val _units: ArrayBuffer[GameUnit] = ArrayBuffer.empty[GameUnit]
  private val _actionBars: mutable.Map[GameUnit, Double] = mutable.Map.empty[GameUnit, Double]

  /** @inheritdoc */
  def addUnit(unit: GameUnit): Unit =
    if !_units.contains(unit) then
      _units += unit
      _actionBars(unit) = 0.0

  /** @inheritdoc */
  def removeUnit(unit: GameUnit): Unit =
    _units -= unit
    _actionBars.remove(unit)

  /** @inheritdoc */
  def units: List[GameUnit] = _units.toList

  /** @inheritdoc */
  def maxActionBar(unit: GameUnit): Double =
    unit.maxActionBar

  /** @inheritdoc */
  def currentActionBar(unit: GameUnit): Double =
    _actionBars.getOrElse(unit, 0.0)

  /** @inheritdoc */
  def resetActionBar(unit: GameUnit): Unit =
    if _units.contains(unit) then
      _actionBars(unit) = 0.0

  /** @inheritdoc */
  def increaseActionBars(amount: Double): Unit =
    if amount > 0.0 then
      for u <- _units do
        val current = _actionBars.getOrElse(u, 0.0)
        _actionBars(u) = current + amount

  /** @inheritdoc */
  def hasCompletedActionBar(unit: GameUnit): Boolean =
    if _units.contains(unit) then
      currentActionBar(unit) >= maxActionBar(unit)
    else
      false

  /** @inheritdoc */
  def completedUnits: List[GameUnit] =
    val ready = _units.filter(hasCompletedActionBar)
    ready.sortBy(u => -(currentActionBar(u) - maxActionBar(u))).toList

  /** @inheritdoc */
  def currentTurnUnit: Option[GameUnit] =
    completedUnits.headOption
