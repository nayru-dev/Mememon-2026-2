package cl.uchile.dcc
package mememon.actions

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.board.NormalPanel
import mememon.exceptions.{InvalidActionException, InvalidMovementException}
import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class AttackAndMoveActionTest extends FunSuite:
  var attack: AttackAction = uninitialized
  var move: MoveAction = uninitialized
  var knight: Knight = uninitialized
  var archer: Archer = uninitialized
  var thief: Thief = uninitialized
  var blackMage: BlackMage = uninitialized
  var whiteMage: WhiteMage = uninitialized
  var enemy: Enemy = uninitialized
  var origin: NormalPanel = uninitialized
  var destination: NormalPanel = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    attack = new AttackAction
    move = new MoveAction
    knight = Knight("Aragorn", 120, 20, 15)
    archer = Archer("Legolas", 90, 10, 10)
    thief = Thief("KBarriga", 85, 12, 8)
    blackMage = BlackMage("Saruman", 65, 6, 5, 80)
    whiteMage = WhiteMage("Gandalf", 70, 8, 6, 60)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    origin = NormalPanel(1, 1)
    destination = NormalPanel(1, 2)
    origin.addNextPanel(destination)

  test("Actions know their names"):
    assertEquals(attack.name, "Attack")
    assertEquals(move.name, "Move")

  test("Every unit has the attack and move actions"):
    assertEquals(knight.actions.length, 2)
    assertEquals(enemy.actions.length, 2)
    assertEquals(knight.actions.head.name, "Attack")
    assertEquals(knight.actions.last.name, "Move")

  test("A unit finds its actions by name"):
    assertEquals(knight.findAction("Attack"), Some(knight.actions.head))
    assertEquals(enemy.findAction("Move"), Some(enemy.actions.last))

  test("Searching an unknown action returns None"):
    assertEquals(knight.findAction("Rasengan"), None)

  test("A knight attacks through doAction"):
    knight.equip(Sword("Master Sword", 35, 10))
    knight.doAction(attack, enemy)
    assertEquals(enemy.hp, 55)

  test("An archer attacks through doAction"):
    archer.equip(Bow("Arco de luz", 20, 7))
    archer.doAction(attack, enemy)
    assertEquals(enemy.hp, 70)

  test("A thief attacks through doAction"):
    thief.equip(Dagger("Athame", 15, 2))
    thief.doAction(attack, enemy)
    assertEquals(enemy.hp, 75)

  test("A black mage attacks through doAction"):
    blackMage.equip(Wand("Patricia", 12, 0, 100))
    blackMage.doAction(attack, enemy)
    assertEquals(enemy.hp, 78)

  test("A white mage attacks through doAction"):
    whiteMage.equip(Staff("Bakulon", 14, 12, 25))
    whiteMage.doAction(attack, enemy)
    assertEquals(enemy.hp, 76)

  test("An enemy attacks through doAction"):
    enemy.doAction(attack, archer)
    assertEquals(archer.hp, 82)

  test("A panel cannot be attacked"):
    val exception = intercept[InvalidActionException](enemy.doAction(attack, origin))

    assertEquals(exception.getMessage, "Orco cannot attack this target.")

  test("Every kind of unit moves through doAction"):
    origin.addUnit(knight)
    origin.addUnit(archer)
    origin.addUnit(thief)
    origin.addUnit(blackMage)
    origin.addUnit(whiteMage)
    origin.addUnit(enemy)

    knight.doAction(move, destination)
    archer.doAction(move, destination)
    thief.doAction(move, destination)
    blackMage.doAction(move, destination)
    whiteMage.doAction(move, destination)
    enemy.doAction(move, destination)

    assertEquals(origin.units, List.empty)
    assertEquals(destination.units, List(enemy, whiteMage, blackMage, thief, archer, knight))

  test("A moved unit knows its new panel"):
    origin.addUnit(knight)

    knight.doAction(move, destination)

    assertEquals(knight.currentPanel, Some(destination))

  test("A unit cannot move to a non adjacent panel"):
    val farPanel = NormalPanel(5, 5)
    origin.addUnit(knight)

    intercept[InvalidMovementException](knight.doAction(move, farPanel))

    assertEquals(knight.currentPanel, Some(origin))
    assertEquals(origin.units, List(knight))
    assertEquals(farPanel.units, List.empty)

  test("A unit that is not on the board cannot move"):
    val exception = intercept[InvalidMovementException](knight.doAction(move, destination))

    assertEquals(exception.getMessage, "Aragorn is not on the board.")

  test("A unit cannot move to another unit"):
    val exception = intercept[InvalidActionException](knight.doAction(move, enemy))

    assertEquals(exception.getMessage, "Aragorn cannot move to this target.")
