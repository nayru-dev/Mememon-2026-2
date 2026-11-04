package cl.uchile.dcc
package mememon.items

import munit.FunSuite
import mememon.potions.{DefensePotion, HealingPotion, MagicAttackPotion, ManaPotion}
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class UsableTest extends FunSuite:

  test("Every weapon and potion can be handled as a Usable"):
    val usables: List[Usable] = List(
      Sword("Sword", 10, 5),
      Dagger("Dagger", 5, 2),
      Bow("Bow", 8, 3),
      Wand("Wand", 2, 1, 20),
      Staff("Staff", 4, 6, 30),
      HealingPotion("Healing"),
      DefensePotion("Defense"),
      ManaPotion("Mana"),
      MagicAttackPotion("Magic Attack")
    )

    val names = usables.map(usable => usable.name)

    assertEquals(
      names,
      List("Sword", "Dagger", "Bow", "Wand", "Staff", "Healing", "Defense", "Mana", "Magic Attack")
    )
