package cl.uchile.dcc.mememon.weapons

import munit.FunSuite
import scala.compiletime.uninitialized

class WeaponTest extends FunSuite:
  var sword: Sword = uninitialized
  var dagger: Dagger = uninitialized
  var bow: Bow= uninitialized
  var wand: Wand= uninitialized
  var staff: Staff= uninitialized

  override def beforeEach(context: BeforeEach): Unit=
    sword = Sword(name= "Master Sword", attack=35, weight=10)
    dagger = Dagger("Athame", 5, 2)
    bow = Bow("Arco de luz", 20, 7)
    wand= Wand("Patricia", 2, 0, 100)
    staff= Staff("Bakulon", 14, 12, 25)

  test("Sword initializes correctly"):
    assertEquals(sword.attack, 35)
    assertEquals(sword.weight, 10)
    assertEquals(sword.name, "Master Sword")

  test("Dagger initializes correctly"):
    assertEquals(dagger.attack, 5)
    assertEquals(dagger.weight, 2)
    assertEquals(dagger.name, "Athame")

  test("Bow initializes correctly"):
    assertEquals(bow.attack, 20)
    assertEquals(bow.weight, 7)
    assertEquals(bow.name, "Arco de luz")

  test("Wand initializes correctly"):
    assertEquals(wand.attack, 2)
    assertEquals(wand.weight, 0)
    assertEquals(wand.name, "Patricia")
    assertEquals(wand.magicAttack, 100)

  test("Staff initializes correctly"):
    assertEquals(staff.attack, 14)
    assertEquals(staff.weight, 12)
    assertEquals(staff.name, "Bakulon")
    assertEquals(staff.magicAttack, 25)