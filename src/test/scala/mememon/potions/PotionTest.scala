package cl.uchile.dcc
package mememon.potions

import munit.FunSuite
import scala.compiletime.uninitialized

class PotionTest extends FunSuite:
  var healingPotion: HealingPotion= uninitialized
  var defensePotion: DefensePotion= uninitialized
  var manaPotion: ManaPotion = uninitialized
  var magicAttackPotion: MagicAttackPotion = uninitialized

  override def beforeEach(context: BeforeEach): Unit=
    healingPotion= HealingPotion("Poción curación grande")
    defensePotion= DefensePotion("Poción Escudo")
    manaPotion= ManaPotion("Poción de maná pequeña")
    magicAttackPotion= MagicAttackPotion("Poción atk mágico")

  test("HealingPotion initializes correctly with name"):
    assertEquals(healingPotion.name, "Poción curación grande")

  test("DefensePotion initializes correctly with name"):
    assertEquals(defensePotion.name, "Poción Escudo")

  test("ManaPotion initializes correctly with name"):
    assertEquals(manaPotion.name, "Poción de maná pequeña")

  test("MagicAttackPotion initializes correctly with name"):
    assertEquals(magicAttackPotion.name, "Poción atk mágico")
    
  




