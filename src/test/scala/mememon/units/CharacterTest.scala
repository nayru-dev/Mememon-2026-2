package cl.uchile.dcc
package mememon.units

import munit.FunSuite
import scala.compiletime.uninitialized

class CharacterTest extends FunSuite:
  var knight: Knight= uninitialized
  var blackMage: BlackMage= uninitialized
  var enemy: Enemy= uninitialized

  override def beforeEach(context: BeforeEach): Unit=
    knight= Knight("Link", 60, 20, 60)
    blackMage= BlackMage("Saruman", 100, 10, 50, 100)
    enemy= Enemy("Orco", 70, 35, 78, 30)

  test("Knight initializes correctly"):
    assertEquals(knight.name, "Link")
    assertEquals(knight.hp, 60)
    assertEquals(knight.defense, 20)
    assertEquals(knight.weight, 60)
    assertEquals(knight.currentWeapon, None)
    assertEquals(knight.inventory, List.empty)

  test("BlackMage initializes correctly with mana"):
    assertEquals(blackMage.name, "Saruman")
    assertEquals(blackMage.hp, 100)
    assertEquals(blackMage.defense, 10)
    assertEquals(blackMage.weight, 50)
    assertEquals(blackMage.mana, 100)

  test("Enemy initializes correctly"):
    assertEquals(enemy.name, "Orco")
    assertEquals(enemy.hp, 70)
    assertEquals(enemy.defense, 35)
    assertEquals(enemy.weight, 78)
    assertEquals(enemy.attack, 30)