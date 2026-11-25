package cl.uchile.dcc
package controller

import munit.FunSuite
import scala.compiletime.uninitialized
import scala.util.Random
import mememon.units.Knight

class RandomGameTest extends FunSuite:
  val characterNames: List[String] = List("Knight", "Archer", "Thief", "Black Mage", "White Mage")
  var controller: GameController = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    controller = GameController(20.0)

  test("The team size must be positive"):
    intercept[IllegalArgumentException](controller.startRandomGame(0, new Random(42)))
    intercept[IllegalArgumentException](controller.startRandomGame(-3, new Random(42)))

  test("A random game has a team of heroes and a team of enemies of the same size"):
    controller.startRandomGame(4, new Random(42))

    assertEquals(controller.players.map(player => player.name), List("Heroes", "Enemies"))
    assertEquals(controller.players.head.units.length, 4)
    assertEquals(controller.players(1).units.length, 4)
    assertEquals(controller.units.length, 8)

  test("Heroes are random characters and enemies are random enemies"):
    controller.startRandomGame(5, new Random(42))

    val heroes = controller.players.head.units
    val enemies = controller.players(1).units
    assert(heroes.forall(unit => characterNames.contains(unit.name)))
    assert(enemies.forall(unit => unit.name == "Enemy"))

  test("Every hero of a random game can attack from the start"):
    controller.startRandomGame(5, new Random(42))

    val enemies = controller.players(1).units
    for hero <- controller.players.head.units do
      hero.doAction(hero.findAction("Attack").get, enemies.head)
    assert(enemies.head.hp < enemies.head.maxHp)

  test("Starting a random game resets the previous one"):
    val aragorn = Knight("Aragorn", 120, 20, 15)
    controller.addUnit(aragorn)
    controller.nextTurn()

    controller.startRandomGame(2, new Random(42))

    assert(!controller.units.contains(aragorn))
    assertEquals(controller.currentUnit, None)
    assertEquals(controller.players.length, 2)

  test("A random game can be played turn by turn"):
    controller.startRandomGame(3, new Random(42))

    controller.nextTurn()

    assert(controller.currentUnit.nonEmpty)
    assert(controller.units.contains(controller.currentUnit.get))

  test("The same seed creates the same game"):
    val other = GameController(20.0)

    controller.startRandomGame(4, new Random(7))
    other.startRandomGame(4, new Random(7))

    assertEquals(controller.units.map(unit => unit.name), other.units.map(unit => unit.name))
    assertEquals(controller.units.map(unit => unit.hp), other.units.map(unit => unit.hp))

  test("A random game can be started without giving a generator"):
    controller.startRandomGame(3)

    assertEquals(controller.units.length, 6)

  test("The game ends when every random enemy is defeated"):
    controller.startRandomGame(2, new Random(42))

    for enemy <- controller.players(1).units do
      enemy.takeDamage(enemy.hp)

    assert(controller.isGameOver)
    assertEquals(controller.gameOverMessage, Some("Game over: Heroes wins."))
