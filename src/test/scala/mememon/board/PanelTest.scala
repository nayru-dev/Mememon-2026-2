package cl.uchile.dcc
package mememon.board

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.Knight

class PanelTest extends FunSuite:
  var panel1: NormalPanel = uninitialized
  var panel2: NormalPanel = uninitialized
  var knight: Knight = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    panel1 = NormalPanel()
    panel2 = NormalPanel()
    knight = Knight("Aragorn", 100, 50, 40)

  test("Panel initializes without character and without next panels"):
    assertEquals(panel1.characters, None)
    assertEquals(panel1.nextPanels, List.empty)

  test("Panel can hold a character"):
    panel1.characters = Some(knight)
    assertEquals(panel1.characters, Some(knight))

  test("Panel can add adjacent panels"):
    panel1.addNextPanel(panel2)
    assertEquals(panel1.nextPanels.length, 1)
    assertEquals(panel1.nextPanels.contains(panel2), true)

  test("Panel does not add duplicate adjacent panels"):
    panel1.addNextPanel(panel2)
    panel1.addNextPanel(panel2)
    assertEquals(panel1.nextPanels.length, 1)