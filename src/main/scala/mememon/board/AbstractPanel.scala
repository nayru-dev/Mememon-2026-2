package cl.uchile.dcc
package mememon.board

import mememon.units.Character
import mememon.exceptions.InvalidMovementException

/** Abstract base implementation of a board panel. */
abstract class AbstractPanel extends Panel:
  /** Holds the character occupying this tile, initialized to None. */
  var characters: Option[Character] = None
  private var _nextPanels: List[Panel] = List.empty

  /** @inheritdoc */
  def nextPanels: List[Panel] = _nextPanels

  /** @inheritdoc */
  def addNextPanel(panel: Panel): Unit =
    if !_nextPanels.contains(panel) then
      _nextPanels = _nextPanels :+ panel

  /** @inheritdoc */
  def moveCharacterTo(target: Panel): Unit =
    if !_nextPanels.contains(target) then
      throw new InvalidMovementException("The target panel is not adjacent to the current panel.")

    characters match
      case Some(c) =>
        target.characters = Some(c)
        characters = None
      case None =>
        throw new InvalidMovementException("There is no character on this panel to move.")
