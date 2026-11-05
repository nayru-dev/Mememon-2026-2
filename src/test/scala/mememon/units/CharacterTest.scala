package cl.uchile.dcc
package mememon.units

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.potions.HealingPotion
import mememon.weapons.{Sword, Bow}

class CharacterTest extends FunSuite:
  var knight: Knight = uninitialized
  var archer: Archer = uninitialized
  var thief: Thief = uninitialized
  var whiteMage: WhiteMage = uninitialized
  var blackMage: BlackMage = uninitialized
  var enemy: Enemy = uninitialized
  var sword: Sword = uninitialized
  var bow: Bow = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    knight = Knight("Aragorn", 120, 20, 15)
    archer = Archer("Legolas", 90, 10, 10)
    thief = Thief("KBarriga", 85, 12, 8)
    whiteMage = WhiteMage("Gandalf", 70, 8, 6, 60)
    blackMage = BlackMage("Saruman", 65, 6, 5, 80)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    sword = Sword("Buster Sword", 30, 10)
    bow = Bow("Hunter Bow", 20, 4)

  test("Playable characters initialize correctly"):
    assertEquals(knight.name, "Aragorn")
    assertEquals(knight.hp, 120)
    assertEquals(knight.defense, 20)
    assertEquals(knight.weight, 15)
    assertEquals(knight.currentWeapon, None)
    assertEquals(knight.inventory, List.empty)
    assertEquals(archer.name, "Legolas")
    assertEquals(thief.name, "KBarriga")

  test("Character stores weapons and potions in its inventory"):
    val potion = HealingPotion("Small Potion")

    knight.addToInventory(sword)
    knight.addToInventory(potion)

    assertEquals(knight.inventory, List(potion, sword))

  test("Inventories are not shared between characters"):
    knight.addToInventory(sword)

    assertEquals(archer.inventory, List.empty)

  test("Magic characters can store usable objects too"):
    val potion = HealingPotion("Small Potion")

    whiteMage.addToInventory(potion)

    assertEquals(whiteMage.inventory, List(potion))
    assertEquals(blackMage.inventory, List.empty)

  test("Archer and thief initialize all their attributes"):
    assertEquals(archer.hp, 90)
    assertEquals(archer.defense, 10)
    assertEquals(archer.weight, 10)
    assertEquals(thief.hp, 85)
    assertEquals(thief.defense, 12)
    assertEquals(thief.weight, 8)

  test("Magic characters initialize correctly with mana"):
    assertEquals(whiteMage.name, "Gandalf")
    assertEquals(whiteMage.hp, 70)
    assertEquals(whiteMage.defense, 8)
    assertEquals(whiteMage.weight, 6)
    assertEquals(whiteMage.mana, 60)
    assertEquals(blackMage.name, "Saruman")
    assertEquals(blackMage.hp, 65)
    assertEquals(blackMage.defense, 6)
    assertEquals(blackMage.weight, 5)
    assertEquals(blackMage.mana, 80)

  test("Units with negative initial hp start at zero"):
    val fallenKnight = Knight("Aragorn", -10, 20, 15)
    val fallenEnemy = Enemy("Orco", -1, 10, 14, 18)

    assertEquals(fallenKnight.hp, 0)
    assertEquals(fallenEnemy.hp, 0)

  test("Negative damage does not heal a unit that is alive"):
    knight.takeDamage(-20)
    assertEquals(knight.hp, 120)

    enemy.takeDamage(-20)
    assertEquals(enemy.hp, 80)

  test("Enemy can attack another enemy"):
    val urukHai = Enemy("Uruk-hai", 40, 5, 10, 12)

    enemy.attack(urukHai)

    assertEquals(urukHai.hp, 27)

  test("Attack equal to defense deals no damage"):
    val shieldedOrc = Enemy("Uruk-hai", 40, 18, 10, 12)

    enemy.attack(shieldedOrc)

    assertEquals(shieldedOrc.hp, 40)

  test("Enemy initializes correctly with attack damage"):
    assertEquals(enemy.name, "Orco")
    assertEquals(enemy.hp, 80)
    assertEquals(enemy.defense, 10)
    assertEquals(enemy.weight, 14)
    assertEquals(enemy.attackDamage, 18)

  test("Damage calculation and hp reduction"):
    knight.takeDamage(50)
    assertEquals(knight.hp, 70)
    knight.takeDamage(100)
    assertEquals(knight.hp, 0)
    knight.takeDamage(-20)
    assertEquals(knight.hp, 0)

  test("Enemy takes damage down to zero"):
    enemy.takeDamage(30)
    assertEquals(enemy.hp, 50)
    enemy.takeDamage(100)
    assertEquals(enemy.hp, 0)
    enemy.takeDamage(-10)
    assertEquals(enemy.hp, 0)

  test("Unarmed character attack damage is zero"):
    assertEquals(knight.attackDamage, 0)
    knight.attack(enemy)
    assertEquals(enemy.hp, 80)

  test("Enemy attack deals physical damage considering defense"):
    enemy.attack(knight)
    assertEquals(knight.hp, 120)

    val trainee = Knight("Trainee", 50, 5, 10)
    enemy.attack(trainee)
    assertEquals(trainee.hp, 37)

  test("maxActionBar calculation without equipped weapon"):
    assertEquals(knight.maxActionBar, 15.0)
    assertEquals(enemy.maxActionBar, 14.0)

  test("Physical attack branch when defense exceeds attack damage"):
    val superTank = Knight("Tank", 100, 50, 20)
    enemy.attack(superTank)
    assertEquals(superTank.hp, 100)
