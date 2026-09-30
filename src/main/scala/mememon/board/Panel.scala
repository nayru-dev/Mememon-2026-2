package cl.uchile.dcc
package mememon.board

import mememon.units.Character

/** Represents a board tile where characters can position themselves. */
trait Panel:
  /** Returns the character currently occupying this panel, or None if empty. */
  def characters: Option[Character]

  /** Returns the list of adjacent panels to which a character can move. */
  def nextPanels: List[Panel]

  /** Connects an adjacent panel to this panel.
   *
   * @param panel the neighboring tile to connect
   */
  def addNextPanel(panel: Panel): Unit

/** Abstract base implementation of a board panel. */
abstract class AbstractPanel extends Panel:
  /** Holds the character occupying this tile, initialized to None. */
  var characters: Option[Character] = None
  private var _nextPanels: List[Panel] = List.empty

  def nextPanels: List[Panel] = _nextPanels

  def addNextPanel(panel: Panel): Unit =
    if !_nextPanels.contains(panel) then
      _nextPanels = _nextPanels :+ panel

/** Represents a standard neutral panel on the board. */
class NormalPanel extends AbstractPanel