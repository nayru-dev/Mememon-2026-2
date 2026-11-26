package cl.uchile.dcc
package mememon.players

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.{Enemy, Knight}

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

  test("Player can control characters and enemies in the same team"):
    val fallenKnight = Knight("Pepidead", 0, 10, 15)
    val orc = Enemy("Orco", 80, 10, 14, 18)
    val mixedPlayer = Player("Luigi", List(fallenKnight, orc))

    assertEquals(mixedPlayer.units, List(fallenKnight, orc))
    assertEquals(mixedPlayer.isDefeated, false)

    orc.takeDamage(80)
    assertEquals(mixedPlayer.isDefeated, true)

  test("Player with no units counts as defeated"):
    val emptyPlayer = Player("Sauron", List.empty)

    assertEquals(emptyPlayer.isDefeated, true)
    
  








