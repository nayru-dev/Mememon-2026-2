package cl.uchile.dcc
package mememon.PanelTest

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.Knight

class PanelTest extends FunSuite:
  var panel1: NormalPanel= uninitialized
  var panel2: NormalPanel = uninitialized

  override def beforeEach(context: BeforeEach): Unit= {
    panel1= NormalPanel()
    panel2= NormalPanel()