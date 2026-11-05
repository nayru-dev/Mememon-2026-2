package cl.uchile.dcc
package mememon.actions.spells

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.board.NormalPanel
import mememon.exceptions.{InvalidActionException, InvalidRequirementException}
import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}
import mememon.weapons.{Dagger, Staff, Wand}

class BlackSpellTest extends FunSuite:
  var thunder: ThunderSpell = uninitialized
  var meteor: MeteorSpell = uninitialized
  var saruman: BlackMage = uninitialized
  var aragorn: Knight = uninitialized
  var enemy: Enemy = uninitialized
  var staff: Staff = uninitialized
  var panel: NormalPanel = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    thunder = new ThunderSpell
    meteor = new MeteorSpell
    saruman = BlackMage("Saruman", 65, 6, 5, 80)
    aragorn = Knight("Aragorn", 120, 20, 15)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    staff = Staff("Bakulon", 14, 12, 25)
    panel = NormalPanel(1, 1)
    saruman.equip(staff)

  test("Black spells know their name and cost"):
    assertEquals(thunder.name, "Thunder")
    assertEquals(thunder.manaCost, 20)
    assertEquals(meteor.name, "Meteor")
    assertEquals(meteor.manaCost, 50)

  test("Only the black mage has the black spells"):
    assertEquals(saruman.findAction("Thunder").get.name, "Thunder")
    assertEquals(saruman.findAction("Meteor").get.name, "Meteor")
    assertEquals(saruman.actions.length, 6)
    assertEquals(aragorn.findAction("Thunder"), None)
    assertEquals(WhiteMage("Gandalf", 70, 8, 6, 60).findAction("Meteor"), None)

  test("Thunder reduces hp by the magical damage, ignoring defense"):
    saruman.doAction(thunder, enemy)

    assertEquals(enemy.hp, 55)

  test("Thunder can target a character too"):
    saruman.doAction(thunder, aragorn)

    assertEquals(aragorn.hp, 95)

  test("Thunder costs 20 mana"):
    saruman.doAction(thunder, enemy)

    assertEquals(saruman.mana, 60)

  test("Thunder cannot target a panel and spends nothing"):
    val exception = intercept[InvalidActionException](saruman.doAction(thunder, panel))

    assertEquals(exception.getMessage, "Saruman cannot cast Thunder on this target.")
    assertEquals(saruman.mana, 80)

  test("Meteor damages every unit on the panel, ignoring defense"):
    panel.addUnit(aragorn)
    panel.addUnit(enemy)

    saruman.doAction(meteor, panel)

    assertEquals(aragorn.hp, 95)
    assertEquals(enemy.hp, 55)

  test("Meteor does not damage units on other panels"):
    val otherPanel = NormalPanel(2, 2)
    otherPanel.addUnit(enemy)

    saruman.doAction(meteor, panel)

    assertEquals(enemy.hp, 80)

  test("Meteor costs 50 mana, even on an empty panel"):
    saruman.doAction(meteor, panel)

    assertEquals(saruman.mana, 30)

  test("Meteor also damages the caster if it is on the panel"):
    panel.addUnit(saruman)

    saruman.doAction(meteor, panel)

    assertEquals(saruman.hp, 40)

  test("Meteor cannot target a unit and spends nothing"):
    val exception = intercept[InvalidActionException](saruman.doAction(meteor, enemy))

    assertEquals(exception.getMessage, "Saruman cannot cast Meteor on this target.")
    assertEquals(enemy.hp, 80)
    assertEquals(saruman.mana, 80)

  test("A spell can be cast with exactly the needed mana"):
    saruman.spendMana(60)

    saruman.doAction(thunder, enemy)

    assertEquals(saruman.mana, 0)
    assertEquals(enemy.hp, 55)

  test("A spell fails without enough mana and changes nothing"):
    saruman.spendMana(61)

    val exception = intercept[InvalidRequirementException](saruman.doAction(thunder, enemy))

    assertEquals(exception.getMessage, "Saruman needs 20 mana to cast Thunder but only has 19.")
    assertEquals(enemy.hp, 80)
    assertEquals(saruman.mana, 19)

  test("A mage without a weapon cannot cast spells"):
    val unarmedMage = BlackMage("Saruman", 65, 6, 5, 80)

    val exception = intercept[InvalidRequirementException](unarmedMage.doAction(thunder, enemy))

    assertEquals(exception.getMessage, "Saruman has no weapon to cast spells.")
    assertEquals(unarmedMage.mana, 80)

  test("A mage with a non magic weapon cannot cast spells"):
    val daggerMage = BlackMage("Saruman", 65, 6, 5, 80)
    daggerMage.equip(Dagger("Athame", 5, 2))

    val exception = intercept[InvalidRequirementException](daggerMage.doAction(thunder, enemy))

    assertEquals(exception.getMessage, "Athame is not a magic weapon.")
    assertEquals(daggerMage.mana, 80)

  test("A wand also gives its magical power"):
    saruman.equip(Wand("Patricia", 2, 0, 100))

    saruman.doAction(thunder, enemy)

    assertEquals(enemy.hp, 0)

  test("A boosted spell deals 50% more damage and the boost is lost"):
    saruman.boostNextSpell()
    assertEquals(saruman.magicDamage, 37)

    saruman.doAction(thunder, enemy)
    assertEquals(enemy.hp, 43)
    assertEquals(saruman.hasSpellBoost, false)

    saruman.doAction(thunder, enemy)
    assertEquals(enemy.hp, 18)

  test("A failed spell keeps the boost"):
    saruman.boostNextSpell()

    intercept[InvalidActionException](saruman.doAction(thunder, panel))

    assertEquals(saruman.hasSpellBoost, true)

  test("Other sources cannot cast black spells"):
    val legolas = Archer("Legolas", 90, 10, 10)
    val kBarriga = Thief("KBarriga", 85, 12, 8)
    val gandalf = WhiteMage("Gandalf", 70, 8, 6, 60)

    val knightError = intercept[InvalidActionException](aragorn.doAction(thunder, enemy))
    val archerError = intercept[InvalidActionException](legolas.doAction(thunder, enemy))
    val thiefError = intercept[InvalidActionException](kBarriga.doAction(thunder, enemy))
    val whiteMageError = intercept[InvalidActionException](gandalf.doAction(meteor, panel))
    val enemyError = intercept[InvalidActionException](enemy.doAction(thunder, aragorn))

    assertEquals(knightError.getMessage, "Aragorn cannot perform Thunder.")
    assertEquals(archerError.getMessage, "Legolas cannot perform Thunder.")
    assertEquals(thiefError.getMessage, "KBarriga cannot perform Thunder.")
    assertEquals(whiteMageError.getMessage, "Gandalf cannot perform Meteor.")
    assertEquals(enemyError.getMessage, "Orco cannot perform Thunder.")
