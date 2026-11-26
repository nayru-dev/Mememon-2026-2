package cl.uchile.dcc
package mememon.actions.spells

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.board.NormalPanel
import mememon.exceptions.{InvalidActionException, InvalidRequirementException}
import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}
import mememon.weapons.{Bow, Staff}

class WhiteSpellTest extends FunSuite:
  var healing: HealingSpell = uninitialized
  var purification: PurificationSpell = uninitialized
  var gandalf: WhiteMage = uninitialized
  var aragorn: Knight = uninitialized
  var enemy: Enemy = uninitialized
  var panel: NormalPanel = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    healing = new HealingSpell
    purification = new PurificationSpell
    gandalf = WhiteMage("Gandalf", 70, 8, 6, 60)
    aragorn = Knight("Aragorn", 120, 20, 15)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    panel = NormalPanel(1, 1)
    gandalf.equip(Staff("Bakulon", 14, 12, 25))

  test("White spells know their name"):
    assertEquals(healing.name, "Healing")
    assertEquals(purification.name, "Purification")

  test("Only the white mage has the white spells"):
    assertEquals(gandalf.findAction("Healing").get.name, "Healing")
    assertEquals(gandalf.findAction("Purification").get.name, "Purification")
    assertEquals(gandalf.actions.length, 6)
    assertEquals(aragorn.findAction("Healing"), None)
    assertEquals(BlackMage("Saruman", 65, 6, 5, 80).findAction("Purification"), None)

  test("Healing restores 20% of the maximum hp plus a quarter of the magical damage"):
    aragorn.takeDamage(50)

    gandalf.doAction(healing, aragorn)

    assertEquals(aragorn.hp, 100)

  test("Healing does not exceed the maximum hp"):
    aragorn.takeDamage(10)

    gandalf.doAction(healing, aragorn)

    assertEquals(aragorn.hp, 120)

  test("Healing can target the caster"):
    gandalf.takeDamage(40)

    gandalf.doAction(healing, gandalf)

    assertEquals(gandalf.hp, 50)

  test("Healing costs 15 mana"):
    gandalf.doAction(healing, aragorn)

    assertEquals(gandalf.mana, 45)

  test("Healing can only be received by characters and spends nothing on failure"):
    enemy.takeDamage(20)

    val onEnemy = intercept[InvalidActionException](gandalf.doAction(healing, enemy))
    val onPanel = intercept[InvalidActionException](gandalf.doAction(healing, panel))

    assertEquals(onEnemy.getMessage, "Gandalf cannot cast Healing on this target.")
    assertEquals(onPanel.getMessage, "Gandalf cannot cast Healing on this target.")
    assertEquals(enemy.hp, 60)
    assertEquals(gandalf.mana, 60)

  test("Purification takes 15% of the current hp plus half of the magical damage"):
    gandalf.doAction(purification, enemy)

    assertEquals(enemy.hp, 56)

  test("Purification uses the current hp, not the maximum"):
    enemy.takeDamage(40)

    gandalf.doAction(purification, enemy)

    assertEquals(enemy.hp, 22)

  test("Purification costs 25 mana"):
    gandalf.doAction(purification, enemy)

    assertEquals(gandalf.mana, 35)

  test("Purification can only be received by enemies and spends nothing on failure"):
    val onCharacter = intercept[InvalidActionException](gandalf.doAction(purification, aragorn))
    val onPanel = intercept[InvalidActionException](gandalf.doAction(purification, panel))

    assertEquals(onCharacter.getMessage, "Gandalf cannot cast Purification on this target.")
    assertEquals(onPanel.getMessage, "Gandalf cannot cast Purification on this target.")
    assertEquals(aragorn.hp, 120)
    assertEquals(gandalf.mana, 60)

  test("White spells use the boost of the next spell only once"):
    gandalf.boostNextSpell()

    gandalf.doAction(purification, enemy)
    assertEquals(enemy.hp, 50)
    assertEquals(gandalf.hasSpellBoost, false)

    gandalf.doAction(purification, enemy)
    assertEquals(enemy.hp, 31)

  test("White spells fail without enough mana and change nothing"):
    gandalf.spendMana(46)
    aragorn.takeDamage(50)

    val exception = intercept[InvalidRequirementException](gandalf.doAction(healing, aragorn))

    assertEquals(exception.getMessage, "Gandalf needs 15 mana to cast Healing but only has 14.")
    assertEquals(aragorn.hp, 70)
    assertEquals(gandalf.mana, 14)

  test("A white mage needs a magic weapon to cast"):
    val unarmed = WhiteMage("Gandalf", 70, 8, 6, 60)
    val withBow = WhiteMage("Gandalf", 70, 8, 6, 60)
    withBow.equip(Bow("Arco de luz", 20, 7))

    intercept[InvalidRequirementException](unarmed.doAction(healing, aragorn))
    intercept[InvalidRequirementException](withBow.doAction(purification, enemy))

    assertEquals(unarmed.mana, 60)
    assertEquals(withBow.mana, 60)

  test("Other sources cannot cast white spells"):
    val legolas = Archer("Legolas", 90, 10, 10)
    val kBarriga = Thief("KBarriga", 85, 12, 8)
    val saruman = BlackMage("Saruman", 65, 6, 5, 80)

    val knightError = intercept[InvalidActionException](aragorn.doAction(healing, aragorn))
    val archerError = intercept[InvalidActionException](legolas.doAction(healing, aragorn))
    val thiefError = intercept[InvalidActionException](kBarriga.doAction(purification, enemy))
    val blackMageError = intercept[InvalidActionException](saruman.doAction(healing, aragorn))
    val enemyError = intercept[InvalidActionException](enemy.doAction(purification, enemy))

    assertEquals(knightError.getMessage, "Aragorn cannot perform Healing.")
    assertEquals(archerError.getMessage, "Legolas cannot perform Healing.")
    assertEquals(thiefError.getMessage, "KBarriga cannot perform Purification.")
    assertEquals(blackMageError.getMessage, "Saruman cannot perform Healing.")
    assertEquals(enemyError.getMessage, "Orco cannot perform Purification.")
