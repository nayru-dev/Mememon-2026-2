package cl.uchile.dcc
package mememon.exceptions

import munit.FunSuite
import mememon.board.NormalPanel
import mememon.units.Knight

class ExceptionsTest extends FunSuite:

  test("Panel lanza InvalidMovementException si intenta mover un personaje a un panel no aledaño"):
    val panelOrigen = NormalPanel(1, 1)
    val panelLejano = NormalPanel(5, 5)
    val caballero = Knight("Ragnar", 100, 20, 15)

    panelOrigen.addUnit(caballero)

    intercept[InvalidMovementException]:
      panelOrigen.moveUnit(caballero, panelLejano)

  test("Panel lanza InvalidMovementException si intenta mover desde un panel vacío"):
    val panelOrigen = NormalPanel(1, 1)
    val panelDestino = NormalPanel(1, 2)
    val caballero = Knight("Ragnar", 100, 20, 15)
    panelOrigen.addNextPanel(panelDestino)

    intercept[InvalidMovementException]:
      panelOrigen.moveUnit(caballero, panelDestino)