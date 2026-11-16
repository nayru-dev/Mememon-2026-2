package cl.uchile.dcc
package controller

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.actions.AttackAction
import mememon.actions.spells.MeteorSpell
import mememon.board.NormalPanel
import mememon.players.Player
import mememon.units.{Archer, BlackMage, Enemy, Knight}
import mememon.weapons.{Bow, Staff, Sword}

class GameOverTest extends FunSuite:
  var controller: GameController = uninitialized
  var attack: AttackAction = uninitialized
  var aragorn: Knight = uninitialized
  var legolas: Archer = uninitialized
  var orco: Enemy = uninitialized
  var urukHai: Enemy = uninitialized
  var fellowship: Player = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    controller = GameController(20.0)
    attack = new AttackAction
    aragorn = Knight("Aragorn", 120, 20, 15)
    legolas = Archer("Legolas", 90, 10, 10)
    orco = Enemy("Orco", 80, 10, 14, 18)
    urukHai = Enemy("Uruk-hai", 8, 10, 30, 12)
    aragorn.equip(Sword("Master Sword", 35, 10))
    legolas.equip(Bow("Arco de luz", 20, 7))
    fellowship = Player("Fellowship", List(aragorn, legolas))

  test("A new game is not over"):
    assertEquals(controller.isGameOver, false)
    assertEquals(controller.gameOverMessage, None)
    assertEquals(controller.players, List())

  test("Adding a team registers the team and its units"):
    val horde = Player("Horde", List(orco))

    controller.addPlayer(fellowship)
    controller.addPlayer(horde)

    assertEquals(controller.players, List(fellowship, horde))
    assertEquals(controller.units, List(aragorn, legolas, orco))

  test("Defeating a unit while its team survives does not end the game"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(urukHai, orco)))
    controller.nextTurn()

    controller.executeAction(attack, urukHai)

    assertEquals(controller.isGameOver, false)
    assertEquals(controller.gameOverMessage, None)
    assertEquals(controller.currentUnit, Some(orco))

  test("Defeating the last unit of a team ends the game with the winner"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(urukHai)))
    controller.nextTurn()
    assertEquals(controller.currentUnit, Some(legolas))

    val result = controller.executeAction(attack, urukHai)

    assertEquals(result, true)
    assertEquals(controller.isGameOver, true)
    assertEquals(controller.gameOverMessage, Some("Game over: Fellowship wins."))
    assertEquals(controller.currentUnit, None)

  test("The controller is notified even if the defeat happens outside an action"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(orco)))

    orco.takeDamage(80)

    assertEquals(controller.isGameOver, true)
    assertEquals(controller.gameOverMessage, Some("Game over: Fellowship wins."))

  test("No action can be executed after the game is over"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(orco)))
    controller.nextTurn()
    orco.takeDamage(80)

    val result = controller.executeAction(attack, aragorn)

    assertEquals(result, false)
    assertEquals(aragorn.hp, 120)
    assertEquals(controller.lastError, Some("Game over: Fellowship wins."))

  test("No unit gets a turn after the game is over"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(orco)))
    orco.takeDamage(80)

    controller.nextTurn()

    assertEquals(controller.currentUnit, None)

  test("If both teams fall at once, no team wins"):
    val saruman = BlackMage("Saruman", 20, 6, 5, 80)
    val panel = NormalPanel(1, 1)
    saruman.equip(Staff("Bakulon", 14, 12, 25))
    panel.addUnit(saruman)
    panel.addUnit(urukHai)
    controller.addPlayer(Player("Isengard", List(saruman)))
    controller.addPlayer(Player("Horde", List(urukHai)))
    controller.nextTurn()
    assertEquals(controller.currentUnit, Some(saruman))

    controller.executeAction(new MeteorSpell, panel)

    assertEquals(controller.isGameOver, true)
    assertEquals(controller.gameOverMessage, Some("Game over: no team survived."))

  test("Reset starts a new game that is not over"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(orco)))
    orco.takeDamage(80)

    controller.reset()

    assertEquals(controller.isGameOver, false)
    assertEquals(controller.gameOverMessage, None)
    assertEquals(controller.players, List())
    assertEquals(controller.units, List())

  test("Defeats of units from a previous game do not end the new game"):
    controller.addPlayer(fellowship)
    controller.addPlayer(Player("Horde", List(orco)))
    controller.reset()
    controller.addPlayer(Player("Gondor", List(aragorn)))
    controller.addPlayer(Player("Mordor", List(urukHai)))

    orco.takeDamage(80)
    legolas.takeDamage(90)

    assertEquals(controller.isGameOver, false)
