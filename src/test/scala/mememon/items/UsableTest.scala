package cl.uchile.dcc
package mememon.items

import munit.FunSuite
import mememon.potions.{DefensePotion, HealingPotion, MagicAttackPotion, ManaPotion}
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class UsableTest extends FunSuite:
  private def nameOf(usable: Usable): String = usable.name

  test("Every weapon can be handled as a Usable"):
    assertEquals(nameOf(Sword("Sword", 10, 5)), "Sword")
    assertEquals(nameOf(Dagger("Dagger", 5, 2)), "Dagger")
    assertEquals(nameOf(Bow("Bow", 8, 3)), "Bow")
    assertEquals(nameOf(Wand("Wand", 2, 1, 20)), "Wand")
    assertEquals(nameOf(Staff("Staff", 4, 6, 30)), "Staff")

  test("Every potion can be handled as a Usable"):
    assertEquals(nameOf(HealingPotion("Healing")), "Healing")
    assertEquals(nameOf(DefensePotion("Defense")), "Defense")
    assertEquals(nameOf(ManaPotion("Mana")), "Mana")
    assertEquals(nameOf(MagicAttackPotion("Magic Attack")), "Magic Attack")
