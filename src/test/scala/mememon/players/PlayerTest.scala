package cl.uchile.dcc
package mememon.players

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.Knight

class PlayerTest extends FunSuite:
  var player: Player= uninitialized
  var defeatedPlayer: Player= uninitialized

  override def beforeEach(context: BeforeEach): Unit=
    val aliveKnight= Knight("Pepito",100, 20, 10)
    val fallenKnight= Knight("Pepidead", 0, 10, 15)

    player= Player("ThoMario", List(aliveKnight))
    defeatedPlayer = Player("Mizu", List(fallenKnight))

  test("Player initializes correctly with units"):
    assertEquals(player.name, "ThoMario")
    assertEquals(player.units.length, 1)

  test("Player correctly detects if defeated or alive"):
    assertEquals(player.isDefeated, false)
    assertEquals(defeatedPlayer.isDefeated, true)
    
  








