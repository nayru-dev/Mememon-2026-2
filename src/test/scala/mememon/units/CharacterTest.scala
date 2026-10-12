package cl.uchile.dcc
package mememon.units

import munit.FunSuite
import scala.compiletime.uninitialized
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
    knight = Knight("Arthur", 120, 20, 15)
    archer = Archer("Robin", 90, 10, 10)
    thief = Thief("Locke", 85, 12, 8)
    whiteMage = WhiteMage("Rosa", 70, 8, 6, 60)
    blackMage = BlackMage("Vivi", 65, 6, 5, 80)
    enemy = Enemy("Goblin", 80, 10, 14, 18)
    sword = Sword("Buster Sword", 30, 10)
    bow = Bow("Hunter Bow", 20, 4)

  test("Playable characters initialize correctly"):
    assertEquals(knight.name, "Arthur")
    assertEquals(knight.hp, 120)
    assertEquals(knight.defense, 20)
    assertEquals(knight.weight, 15)
    assertEquals(knight.currentWeapon, None)
    assertEquals(knight.inventory, List.empty)
    assertEquals(archer.name, "Robin")
    assertEquals(thief.name, "Locke")

  test("Magic characters initialize correctly with mana"):
    assertEquals(whiteMage.name, "Rosa")
    assertEquals(whiteMage.mana, 60)
    assertEquals(blackMage.name, "Vivi")
    assertEquals(blackMage.mana, 80)

  test("Enemy initializes correctly with attack damage"):
    assertEquals(enemy.name, "Goblin")
    assertEquals(enemy.hp, 80)
    assertEquals(enemy.defense, 10)
    assertEquals(enemy.weight, 14)
    assertEquals(enemy.attackDamage, 18)
    assertEquals(enemy.currentWeapon, None)

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

  test("maxActionBar calculation with and without equipped weapon"):
    assertEquals(knight.maxActionBar, 15.0)
    assertEquals(enemy.maxActionBar, 14.0)

  test("Physical attack branch when defense exceeds attack damage"):
    val superTank = Knight("Tank", 100, 50, 20)
    enemy.attack(superTank)
    assertEquals(superTank.hp, 100)
