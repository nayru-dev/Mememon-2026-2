package cl.uchile.dcc
package mememon.units

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.exceptions.InvalidRequirementException
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class EquipWeaponTest extends FunSuite:
  var knight: Knight = uninitialized
  var archer: Archer = uninitialized
  var thief: Thief = uninitialized
  var blackMage: BlackMage = uninitialized
  var whiteMage: WhiteMage = uninitialized
  var enemy: Enemy = uninitialized
  var sword: Sword = uninitialized
  var dagger: Dagger = uninitialized
  var bow: Bow = uninitialized
  var wand: Wand = uninitialized
  var staff: Staff = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    knight = Knight("Aragorn", 120, 20, 15)
    archer = Archer("Legolas", 90, 10, 10)
    thief = Thief("KBarriga", 85, 12, 8)
    blackMage = BlackMage("Saruman", 65, 6, 5, 80)
    whiteMage = WhiteMage("Gandalf", 70, 8, 6, 60)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    sword = Sword("Master Sword", 35, 10)
    dagger = Dagger("Athame", 5, 2)
    bow = Bow("Arco de luz", 20, 7)
    wand = Wand("Patricia", 2, 0, 100)
    staff = Staff("Bakulon", 14, 12, 25)

  test("Knight can equip sword, dagger and bow"):
    knight.equip(sword)
    assertEquals(knight.currentWeapon, Some(sword))
    knight.equip(dagger)
    assertEquals(knight.currentWeapon, Some(dagger))
    knight.equip(bow)
    assertEquals(knight.currentWeapon, Some(bow))

  test("Knight cannot equip wand or staff"):
    intercept[InvalidRequirementException](knight.equip(wand))
    intercept[InvalidRequirementException](knight.equip(staff))
    assertEquals(knight.currentWeapon, None)

  test("Archer can equip sword, bow and wand"):
    archer.equip(sword)
    assertEquals(archer.currentWeapon, Some(sword))
    archer.equip(bow)
    assertEquals(archer.currentWeapon, Some(bow))
    archer.equip(wand)
    assertEquals(archer.currentWeapon, Some(wand))

  test("Archer cannot equip dagger or staff"):
    intercept[InvalidRequirementException](archer.equip(dagger))
    intercept[InvalidRequirementException](archer.equip(staff))
    assertEquals(archer.currentWeapon, None)

  test("Thief can equip sword and dagger"):
    thief.equip(sword)
    assertEquals(thief.currentWeapon, Some(sword))
    thief.equip(dagger)
    assertEquals(thief.currentWeapon, Some(dagger))

  test("Thief cannot equip bow, wand or staff"):
    intercept[InvalidRequirementException](thief.equip(bow))
    intercept[InvalidRequirementException](thief.equip(wand))
    intercept[InvalidRequirementException](thief.equip(staff))
    assertEquals(thief.currentWeapon, None)

  test("Black mage can equip dagger, wand and staff"):
    blackMage.equip(dagger)
    assertEquals(blackMage.currentWeapon, Some(dagger))
    blackMage.equip(wand)
    assertEquals(blackMage.currentWeapon, Some(wand))
    blackMage.equip(staff)
    assertEquals(blackMage.currentWeapon, Some(staff))

  test("Black mage cannot equip sword or bow"):
    intercept[InvalidRequirementException](blackMage.equip(sword))
    intercept[InvalidRequirementException](blackMage.equip(bow))
    assertEquals(blackMage.currentWeapon, None)

  test("White mage can equip bow, wand and staff"):
    whiteMage.equip(bow)
    assertEquals(whiteMage.currentWeapon, Some(bow))
    whiteMage.equip(wand)
    assertEquals(whiteMage.currentWeapon, Some(wand))
    whiteMage.equip(staff)
    assertEquals(whiteMage.currentWeapon, Some(staff))

  test("White mage cannot equip sword or dagger"):
    intercept[InvalidRequirementException](whiteMage.equip(sword))
    intercept[InvalidRequirementException](whiteMage.equip(dagger))
    assertEquals(whiteMage.currentWeapon, None)

  test("Equipping a weapon makes the character its owner"):
    knight.equip(sword)

    assertEquals(sword.owner, Some(knight))

  test("Failing to equip keeps the current weapon and gives no ownership"):
    knight.equip(sword)

    intercept[InvalidRequirementException](knight.equip(wand))

    assertEquals(knight.currentWeapon, Some(sword))
    assertEquals(wand.owner, None)

  test("The exception message says who cannot equip what"):
    val exception = intercept[InvalidRequirementException](knight.equip(wand))

    assertEquals(exception.getMessage, "Aragorn cannot equip Patricia.")

  test("An armed character attacks with its weapon"):
    knight.equip(sword)

    assertEquals(knight.attackDamage, 35)
    knight.attack(enemy)
    assertEquals(enemy.hp, 55)

  test("The action bar considers half of the weapon weight"):
    knight.equip(sword)

    assertEquals(knight.maxActionBar, 20.0)

  test("Replacing a weapon updates attack and action bar"):
    knight.equip(sword)
    knight.equip(dagger)

    assertEquals(knight.attackDamage, 5)
    assertEquals(knight.maxActionBar, 16.0)
