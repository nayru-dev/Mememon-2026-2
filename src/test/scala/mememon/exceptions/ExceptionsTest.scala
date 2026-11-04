package cl.uchile.dcc
package mememon.exceptions

import munit.FunSuite
import mememon.board.NormalPanel
import mememon.units.Knight

class ExceptionsTest extends FunSuite:

  test("Panel lanza InvalidMovementException si intenta mover un personaje a un panel no aledaño"):
    val panelOrigen = NormalPanel()
    val panelLejano = NormalPanel()
    val caballero = Knight("Ragnar", 100, 20, 15)

    panelOrigen.characters = Some(caballero)

    intercept[InvalidMovementException]:
      panelOrigen.moveCharacterTo(panelLejano)

  test("Panel lanza InvalidMovementException si intenta mover desde un panel vacío"):
    val panelOrigen = NormalPanel()
    val panelDestino = NormalPanel()
    panelOrigen.addNextPanel(panelDestino)

    intercept[InvalidMovementException]:
      panelOrigen.moveCharacterTo(panelDestino)