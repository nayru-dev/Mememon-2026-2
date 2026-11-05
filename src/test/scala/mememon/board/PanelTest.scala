package cl.uchile.dcc
package mememon.board

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.exceptions.InvalidMovementException
import mememon.units.{Enemy, Knight}

class PanelTest extends FunSuite:
  var panel1: NormalPanel = uninitialized
  var panel2: NormalPanel = uninitialized
  var knight: Knight = uninitialized
  var enemy: Enemy = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    panel1 = NormalPanel(1, 1)
    panel2 = NormalPanel(1, 2)
    knight = Knight("Aragorn", 100, 50, 40)
    enemy = Enemy("Orco", 80, 10, 14, 18)

  test("Panel knows its coordinates"):
    assertEquals(panel1.x, 1)
    assertEquals(panel1.y, 1)
    assertEquals(panel2.x, 1)
    assertEquals(panel2.y, 2)

  test("Panel initializes without units and without next panels"):
    assertEquals(panel1.units, List.empty)
    assertEquals(panel1.nextPanels, List.empty)

  test("Panel can hold characters and enemies at the same time"):
    panel1.addUnit(knight)
    panel1.addUnit(enemy)

    assertEquals(panel1.units, List(enemy, knight))

  test("Panel does not store the same unit twice"):
    panel1.addUnit(knight)
    panel1.addUnit(knight)

    assertEquals(panel1.units, List(knight))

  test("Panel can remove a unit and keeps the others"):
    panel1.addUnit(knight)
    panel1.addUnit(enemy)

    panel1.removeUnit(knight)

    assertEquals(panel1.units, List(enemy))

  test("Removing a unit that is not on the panel has no effect"):
    panel1.addUnit(knight)

    panel1.removeUnit(enemy)

    assertEquals(panel1.units, List(knight))

  test("Units on a panel do not affect other panels"):
    panel1.addUnit(knight)

    assertEquals(panel2.units, List.empty)

  test("Panel can add adjacent panels"):
    panel1.addNextPanel(panel2)

    assertEquals(panel1.nextPanels, List(panel2))

  test("Panel does not add duplicate adjacent panels"):
    panel1.addNextPanel(panel2)
    panel1.addNextPanel(panel2)

    assertEquals(panel1.nextPanels, List(panel2))

  test("A corner panel can have two adjacent panels"):
    val panelRight = NormalPanel(2, 1)

    panel1.addNextPanel(panelRight)
    panel1.addNextPanel(panel2)

    assertEquals(panel1.nextPanels, List(panel2, panelRight))

  test("Adjacency is defined separately for each panel"):
    panel1.addNextPanel(panel2)

    assertEquals(panel2.nextPanels, List.empty)

  test("A unit moves to an adjacent panel and leaves the original one"):
    panel1.addNextPanel(panel2)
    panel1.addUnit(knight)

    panel1.moveUnit(knight, panel2)

    assertEquals(panel1.units, List.empty)
    assertEquals(panel2.units, List(knight))

  test("Moving a unit does not move the others"):
    panel1.addNextPanel(panel2)
    panel1.addUnit(knight)
    panel1.addUnit(enemy)

    panel1.moveUnit(enemy, panel2)

    assertEquals(panel1.units, List(knight))
    assertEquals(panel2.units, List(enemy))

  test("A failed movement to a non adjacent panel changes nothing"):
    panel1.addUnit(knight)

    intercept[InvalidMovementException](panel1.moveUnit(knight, panel2))

    assertEquals(panel1.units, List(knight))
    assertEquals(panel2.units, List.empty)

  test("A failed movement of a unit that is not on the panel changes nothing"):
    panel1.addNextPanel(panel2)

    val exception = intercept[InvalidMovementException](panel1.moveUnit(knight, panel2))

    assertEquals(exception.getMessage, "Aragorn is not on this panel.")
    assertEquals(panel2.units, List.empty)
