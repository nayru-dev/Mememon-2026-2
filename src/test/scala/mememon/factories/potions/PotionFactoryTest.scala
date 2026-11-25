package cl.uchile.dcc
package mememon.factories.potions

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.potions.{DefensePotion, HealingPotion, MagicAttackPotion, ManaPotion}
import mememon.units.Knight

class PotionFactoryTest extends FunSuite:
  var healingFactory: HealingPotionFactory = uninitialized
  var defenseFactory: DefensePotionFactory = uninitialized
  var manaFactory: ManaPotionFactory = uninitialized
  var magicAttackFactory: MagicAttackPotionFactory = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    healingFactory = new HealingPotionFactory
    defenseFactory = new DefensePotionFactory
    manaFactory = new ManaPotionFactory
    magicAttackFactory = new MagicAttackPotionFactory

  test("Healing potion factory creates healing potions"):
    val potion: HealingPotion = healingFactory.make()

    assertEquals(potion.name, "Healing Potion")

  test("Defense potion factory creates defense potions"):
    val potion: DefensePotion = defenseFactory.make()

    assertEquals(potion.name, "Defense Potion")

  test("Mana potion factory creates mana potions"):
    val potion: ManaPotion = manaFactory.make()

    assertEquals(potion.name, "Mana Potion")

  test("Magic attack potion factory creates magic attack potions"):
    val potion: MagicAttackPotion = magicAttackFactory.make()

    assertEquals(potion.name, "Magic Attack Potion")

  test("Every call creates a new potion"):
    val first = healingFactory.make()
    val second = healingFactory.make()

    assert(first != second)

  test("Potion factories can be used through their common contract"):
    val factories: List[PotionFactory] =
      List(healingFactory, defenseFactory, manaFactory, magicAttackFactory)

    val potions = factories.map(factory => factory.make())

    assertEquals(
      potions.map(potion => potion.name),
      List("Healing Potion", "Defense Potion", "Mana Potion", "Magic Attack Potion")
    )

  test("A created potion can be consumed from the inventory"):
    val knight = Knight("Aragorn", 120, 20, 15)
    val potion = healingFactory.make()
    knight.addToInventory(potion)

    knight.doAction(knight.findAction("Consume Potion").get, potion)

    assertEquals(knight.inventory, List())
