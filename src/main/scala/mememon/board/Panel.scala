package cl.uchile.dcc
package mememon.board

import mememon.units.Character

trait Panel:
  def characters: Option[Character]
  def nextPanels: List[Panel]
  def addNextPanel(panel: Panel): Unit

abstract class AbstractPanel extends Panel:
  var characters: Option[Character]= None
  private var _nextPanels: List[Panel]= List.empty

  def nextPanels: List[Panel]= _nextPanels

  def addNextPanel(panel: Panel): Unit=
    if !_nextPanels.contains(panel) then
      _nextPanels= _nextPanels :+ panel
  
class NormalPanel extends AbstractPanel
