package cl.uchile.dcc
package mememon.board

import mememon.actions.Target
import mememon.units.GameUnit

/** Represents a board tile where units can position themselves. */
trait Panel extends Target:
  /** Horizontal coordinate of this panel on the board. */
  val x: Int

  /** Vertical coordinate of this panel on the board. */
  val y: Int

  /** Returns the units currently located on this panel.
   *
   * @return the units on this panel
   */
  def units: List[GameUnit]

  /** Places a unit on this panel, taking it out of its previous panel.
   *
   * It has no effect if the unit is already here.
   *
   * @param unit the unit to place
   */
  def addUnit(unit: GameUnit): Unit

  /** Removes a unit from this panel. It has no effect if the unit is not here.
   *
   * @param unit the unit to remove
   */
  def removeUnit(unit: GameUnit): Unit

  /** Returns the list of adjacent panels to which a unit can move.
   *
   * @return the adjacent panels
   */
  def nextPanels: List[Panel]

  /** Connects an adjacent panel to this panel.
   *
   * @param panel the neighboring tile to connect
   */
  def addNextPanel(panel: Panel): Unit

  /** Moves a unit from this panel to an adjacent target panel.
   *
   * @param unit the unit to move
   * @param target the adjacent panel where the unit will be moved
   * @throws mememon.exceptions.InvalidMovementException if the target is not adjacent or the unit is not on this panel
   */
  def moveUnit(unit: GameUnit, target: Panel): Unit
