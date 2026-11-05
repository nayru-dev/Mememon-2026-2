package cl.uchile.dcc
package mememon.board

import mememon.actions.AbstractTarget
import mememon.units.GameUnit
import mememon.exceptions.InvalidMovementException

/** Abstract base implementation of a board panel.
 *
 * @param x horizontal coordinate of the panel
 * @param y vertical coordinate of the panel
 */
abstract class AbstractPanel(val x: Int, val y: Int) extends AbstractTarget with Panel:
  private var _units: List[GameUnit] = List.empty
  private var _nextPanels: List[Panel] = List.empty

  /** @inheritdoc */
  def units: List[GameUnit] = _units

  /** @inheritdoc */
  def addUnit(unit: GameUnit): Unit =
    if !_units.contains(unit) then
      unit.currentPanel match
        case Some(previous) => previous.removeUnit(unit)
        case None           => ()
      _units = unit :: _units
      unit.setCurrentPanel(Some(this))

  /** @inheritdoc */
  def removeUnit(unit: GameUnit): Unit =
    if _units.contains(unit) then
      _units = _units.filter(current => current != unit)
      unit.setCurrentPanel(None)

  /** @inheritdoc */
  def nextPanels: List[Panel] = _nextPanels

  /** @inheritdoc */
  def addNextPanel(panel: Panel): Unit =
    if !_nextPanels.contains(panel) then
      _nextPanels = panel :: _nextPanels

  /** @inheritdoc */
  def moveUnit(unit: GameUnit, target: Panel): Unit =
    if !_nextPanels.contains(target) then
      throw new InvalidMovementException("The target panel is not adjacent to the current panel.")
    if !_units.contains(unit) then
      throw new InvalidMovementException(s"${unit.name} is not on this panel.")
    target.addUnit(unit)

  /** @inheritdoc */
  override def receiveMove(source: GameUnit): Unit =
    source.currentPanel match
      case Some(origin) => origin.moveUnit(source, this)
      case None         => throw new InvalidMovementException(s"${source.name} is not on the board.")
