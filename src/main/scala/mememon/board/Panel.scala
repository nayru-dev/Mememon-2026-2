package cl.uchile.dcc
package mememon.board

import mememon.units.Character
import mememon.exceptions.InvalidMovementException


/** Represents a board tile where characters can position themselves. */
trait Panel:
  /** Returns or updates the character currently occupying this panel, or None if empty. */
  var characters: Option[Character]

  /** Returns the list of adjacent panels to which a character can move. */
  def nextPanels: List[Panel]

  /** Connects an adjacent panel to this panel.
   *
   * @param panel the neighboring tile to connect
   */
  def addNextPanel(panel: Panel): Unit

  def moveCharacterTo(target: Panel): Unit

/** Abstract base implementation of a board panel. */
abstract class AbstractPanel extends Panel:
  /** Holds the character occupying this tile, initialized to None. */
  var characters: Option[Character] = None
  private var _nextPanels: List[Panel] = List.empty

  def nextPanels: List[Panel] = _nextPanels

  def addNextPanel(panel: Panel): Unit =
    if !_nextPanels.contains(panel) then
      _nextPanels = _nextPanels :+ panel

  def moveCharacterTo(target: Panel): Unit =
    if !_nextPanels.contains(target) then
      throw new InvalidMovementException("The target panel is not adjacent to the current panel.")

    characters match
      case Some(c) =>
        target.characters = Some(c)
        characters = None
      case None =>
        throw new InvalidMovementException("There is no character on this panel to move.")

/** Represents a standard neutral panel on the board. */
class NormalPanel extends AbstractPanel

