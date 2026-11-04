package cl.uchile.dcc
package mememon.board

import mememon.units.Character

/** Represents a board tile where characters can position themselves. */
trait Panel:
  /** Returns or updates the character currently occupying this panel, or None if empty. */
  var characters: Option[Character]

  /** Returns the list of adjacent panels to which a character can move.
   *
   * @return the adjacent panels
   */
  def nextPanels: List[Panel]

  /** Connects an adjacent panel to this panel.
   *
   * @param panel the neighboring tile to connect
   */
  def addNextPanel(panel: Panel): Unit

  /** Moves the character on this panel to an adjacent target panel.
   *
   * @param target the adjacent panel where the character will be moved
   * @throws mememon.exceptions.InvalidMovementException if the target is not adjacent or this panel is empty
   */
  def moveCharacterTo(target: Panel): Unit
