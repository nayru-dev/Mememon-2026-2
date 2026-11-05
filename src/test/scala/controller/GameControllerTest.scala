package cl.uchile.dcc
package controller

import munit.FunSuite
import mememon.actions.{AttackAction, MoveAction}
import mememon.actions.spells.MeteorSpell
import mememon.board.NormalPanel
import mememon.units.{BlackMage, Enemy, Knight, Archer}
import mememon.weapons.{Bow, Staff, Sword}

class GameControllerTest extends FunSuite:
  var controller: GameController = null
  var attack: AttackAction = null
  var aragorn: Knight = null
  var legolas: Archer = null
  var orco: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    controller = GameController(20.0)
    attack = new AttackAction
    aragorn = Knight("Aragorn", 120, 20, 15)
    legolas = Archer("Legolas", 90, 10, 10)
    orco = Enemy("Orco", 80, 10, 14, 18)
    aragorn.equip(Sword("Master Sword", 35, 10))
    legolas.equip(Bow("Arco de luz", 20, 7))

  private def addAllUnits(): Unit =
    controller.addUnit(aragorn)
    controller.addUnit(legolas)
    controller.addUnit(orco)

  test("The action bar increment must be positive"):
    intercept[IllegalArgumentException](GameController(0.0))
    intercept[IllegalArgumentException](GameController(-5.0))

  test("A new controller has no units, turn or error"):
    assertEquals(controller.units, List())
    assertEquals(controller.currentUnit, None)
    assertEquals(controller.lastError, None)

  test("The controller can be created with the default increment"):
    val defaultController = GameController()
    defaultController.addUnit(orco)

    defaultController.nextTurn()

    assertEquals(defaultController.currentUnit, Some(orco))

  test("The next turn of an empty game has no unit"):
    controller.nextTurn()

    assertEquals(controller.currentUnit, None)

  test("The unit with the largest overflow plays first"):
    addAllUnits()

    controller.nextTurn()

    assertEquals(controller.currentUnit, Some(legolas))

  test("The controller knows the actions of a unit"):
    assertEquals(controller.availableActions(aragorn), aragorn.actions)
    assertEquals(controller.availableActions(orco), orco.actions)

  test("A successful action affects the target and ends the turn"):
    addAllUnits()
    controller.nextTurn()

    val result = controller.executeAction(attack, orco)

    assertEquals(result, true)
    assertEquals(orco.hp, 70)
    assertEquals(controller.currentUnit, Some(orco))
    assertEquals(controller.lastError, None)

  test("Turns follow the overflow order and start again"):
    addAllUnits()
    controller.nextTurn()

    controller.executeAction(attack, orco)
    assertEquals(controller.currentUnit, Some(orco))

    controller.executeAction(attack, legolas)
    assertEquals(controller.currentUnit, Some(aragorn))

    controller.executeAction(attack, orco)
    assertEquals(controller.currentUnit, Some(legolas))

  test("A failed action keeps the turn and reports the reason"):
    addAllUnits()
    controller.nextTurn()
    val panel = NormalPanel(1, 1)

    val result = controller.executeAction(attack, panel)

    assertEquals(result, false)
    assertEquals(controller.currentUnit, Some(legolas))
    assertEquals(controller.lastError, Some("Legolas cannot attack this target."))

  test("A movement failure is reported without changing the turn"):
    controller.addUnit(orco)
    controller.nextTurn()
    val destination = NormalPanel(1, 2)

    val result = controller.executeAction(new MoveAction, destination)

    assertEquals(result, false)
    assertEquals(controller.currentUnit, Some(orco))
    assertEquals(controller.lastError, Some("Orco is not on the board."))

  test("A successful action clears the previous error"):
    addAllUnits()
    controller.nextTurn()
    controller.executeAction(attack, NormalPanel(1, 1))

    controller.executeAction(attack, orco)

    assertEquals(controller.lastError, None)

  test("An action without a current unit fails"):
    val result = controller.executeAction(attack, orco)

    assertEquals(result, false)
    assertEquals(orco.hp, 80)
    assertEquals(controller.lastError, Some("There is no unit playing its turn."))

  test("A unit defeated by an action leaves the combat"):
    val urukHai = Enemy("Uruk-hai", 8, 10, 30, 12)
    controller.addUnit(aragorn)
    controller.addUnit(urukHai)
    controller.nextTurn()

    controller.executeAction(attack, urukHai)

    assertEquals(controller.units, List(aragorn))
    assertEquals(controller.currentUnit, Some(aragorn))

  test("Meteor can remove several units, including its caster"):
    val saruman = BlackMage("Saruman", 20, 6, 5, 80)
    val urukHai = Enemy("Uruk-hai", 10, 10, 50, 12)
    val panel = NormalPanel(1, 1)
    saruman.equip(Staff("Bakulon", 14, 12, 25))
    panel.addUnit(saruman)
    panel.addUnit(urukHai)
    controller.addUnit(saruman)
    controller.addUnit(urukHai)
    controller.addUnit(aragorn)
    controller.nextTurn()
    assertEquals(controller.currentUnit, Some(saruman))

    controller.executeAction(new MeteorSpell, panel)

    assertEquals(controller.units, List(aragorn))
    assertEquals(controller.currentUnit, Some(aragorn))

  test("An already defeated unit is removed before choosing the turn"):
    orco.takeDamage(80)
    controller.addUnit(orco)
    controller.addUnit(aragorn)

    controller.nextTurn()

    assertEquals(controller.units, List(aragorn))
    assertEquals(controller.currentUnit, Some(aragorn))

  test("If every unit is defeated there is no current unit"):
    orco.takeDamage(80)
    controller.addUnit(orco)

    controller.nextTurn()

    assertEquals(controller.units, List())
    assertEquals(controller.currentUnit, None)

  test("A small increment reaches the turn after several increases"):
    val slowController = GameController(0.5)
    slowController.addUnit(legolas)

    slowController.nextTurn()

    assertEquals(slowController.currentUnit, Some(legolas))

  test("Reset removes units, turn and error"):
    addAllUnits()
    controller.nextTurn()
    controller.executeAction(attack, NormalPanel(1, 1))

    controller.reset()

    assertEquals(controller.units, List())
    assertEquals(controller.currentUnit, None)
    assertEquals(controller.lastError, None)

  test("A new game can start after a reset"):
    addAllUnits()
    controller.nextTurn()
    controller.reset()

    controller.addUnit(orco)
    controller.nextTurn()

    assertEquals(controller.units, List(orco))
    assertEquals(controller.currentUnit, Some(orco))
