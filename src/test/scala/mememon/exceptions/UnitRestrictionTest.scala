package cl.uchile.dcc
package mememon.exceptions

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.actions.{AttackAction, MoveAction}
import mememon.actions.spells.{HealingSpell, MeteorSpell, PurificationSpell, ThunderSpell}
import mememon.board.NormalPanel
import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}
import mememon.weapons.{Staff, Sword}

class UnitRestrictionTest extends FunSuite:
  var attack: AttackAction = uninitialized
  var aragorn: Knight = uninitialized
  var legolas: Archer = uninitialized
  var kBarriga: Thief = uninitialized
  var saruman: BlackMage = uninitialized
  var gandalf: WhiteMage = uninitialized
  var orco: Enemy = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    attack = new AttackAction
    aragorn = Knight("Aragorn", 120, 20, 15)
    legolas = Archer("Legolas", 90, 10, 10)
    kBarriga = Thief("KBarriga", 85, 12, 8)
    saruman = BlackMage("Saruman", 65, 6, 5, 80)
    gandalf = WhiteMage("Gandalf", 70, 8, 6, 60)
    orco = Enemy("Orco", 80, 10, 14, 18)

  test("Every kind of defeated unit cannot act"):
    aragorn.takeDamage(120)
    legolas.takeDamage(90)
    kBarriga.takeDamage(85)
    saruman.takeDamage(65)
    gandalf.takeDamage(70)
    val target = Enemy("Uruk-hai", 40, 5, 10, 12)
    orco.takeDamage(80)

    intercept[InvalidUnitStateException](aragorn.doAction(attack, target))
    intercept[InvalidUnitStateException](legolas.doAction(attack, target))
    intercept[InvalidUnitStateException](kBarriga.doAction(attack, target))
    intercept[InvalidUnitStateException](saruman.doAction(attack, target))
    intercept[InvalidUnitStateException](gandalf.doAction(attack, target))
    val exception = intercept[InvalidUnitStateException](orco.doAction(attack, target))

    assertEquals(exception.getMessage, "Orco is defeated and cannot act.")
    assertEquals(target.hp, 40)

  test("A defeated unit cannot move"):
    val origin = NormalPanel(1, 1)
    val destination = NormalPanel(1, 2)
    origin.addNextPanel(destination)
    origin.addUnit(orco)
    orco.takeDamage(80)

    intercept[InvalidUnitStateException](orco.doAction(new MoveAction, destination))

    assertEquals(orco.currentPanel, Some(origin))

  test("A unit with one hp can still act"):
    orco.takeDamage(79)

    orco.doAction(attack, legolas)

    assertEquals(orco.hp, 1)
    assertEquals(legolas.hp, 82)

  test("A unit defeated by an attack cannot receive more attacks"):
    val weakOrc = Enemy("Uruk-hai", 8, 10, 10, 12)
    aragorn.equip(Sword("Master Sword", 35, 10))
    aragorn.doAction(attack, weakOrc)
    assertEquals(weakOrc.hp, 0)

    val exception = intercept[InvalidUnitStateException](aragorn.doAction(attack, weakOrc))

    assertEquals(exception.getMessage, "Uruk-hai is defeated and cannot receive actions.")

  test("A defeated unit cannot receive Thunder and the mana is kept"):
    saruman.equip(Staff("Bakulon", 14, 12, 25))
    orco.takeDamage(80)

    intercept[InvalidUnitStateException](saruman.doAction(new ThunderSpell, orco))

    assertEquals(saruman.mana, 80)

  test("A defeated character cannot be healed and the mana is kept"):
    gandalf.equip(Staff("Bakulon", 14, 12, 25))
    aragorn.takeDamage(120)

    val exception = intercept[InvalidUnitStateException](gandalf.doAction(new HealingSpell, aragorn))

    assertEquals(exception.getMessage, "Aragorn is defeated and cannot receive actions.")
    assertEquals(aragorn.hp, 0)
    assertEquals(gandalf.mana, 60)

  test("A defeated enemy cannot receive Purification and the mana is kept"):
    gandalf.equip(Staff("Bakulon", 14, 12, 25))
    orco.takeDamage(80)

    intercept[InvalidUnitStateException](gandalf.doAction(new PurificationSpell, orco))

    assertEquals(gandalf.mana, 60)

  test("Meteor ignores defeated units and still damages the others"):
    val panel = NormalPanel(1, 1)
    saruman.equip(Staff("Bakulon", 14, 12, 25))
    panel.addUnit(orco)
    panel.addUnit(aragorn)
    orco.takeDamage(80)

    saruman.doAction(new MeteorSpell, panel)

    assertEquals(orco.hp, 0)
    assertEquals(aragorn.hp, 95)

  test("Every kind of character needs a weapon to attack"):
    intercept[InvalidRequirementException](legolas.doAction(attack, orco))
    intercept[InvalidRequirementException](kBarriga.doAction(attack, orco))
    intercept[InvalidRequirementException](saruman.doAction(attack, orco))
    intercept[InvalidRequirementException](gandalf.doAction(attack, orco))
    val exception = intercept[InvalidRequirementException](aragorn.doAction(attack, orco))

    assertEquals(exception.getMessage, "Aragorn needs a weapon to attack.")
    assertEquals(orco.hp, 80)

  test("Enemies do not need a weapon to attack"):
    orco.doAction(attack, legolas)

    assertEquals(legolas.hp, 82)
