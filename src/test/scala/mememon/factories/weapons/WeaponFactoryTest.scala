package cl.uchile.dcc
package mememon.factories.weapons

import munit.FunSuite
import scala.compiletime.uninitialized
import scala.util.Random
import mememon.units.{BlackMage, Knight}
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class WeaponFactoryTest extends FunSuite:
  val tries: Int = 50
  var swordFactory: SwordFactory = uninitialized
  var daggerFactory: DaggerFactory = uninitialized
  var bowFactory: BowFactory = uninitialized
  var wandFactory: WandFactory = uninitialized
  var staffFactory: StaffFactory = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    val random = new Random(42)
    swordFactory = new SwordFactory(random)
    daggerFactory = new DaggerFactory(random)
    bowFactory = new BowFactory(random)
    wandFactory = new WandFactory(random)
    staffFactory = new StaffFactory(random)

  test("Sword factory creates swords with stats inside their ranges"):
    for i <- 1 to tries do
      val sword: Sword = swordFactory.make()
      assertEquals(sword.name, "Sword")
      assert(sword.attack >= 25 && sword.attack <= 40)
      assert(sword.weight >= 8 && sword.weight <= 12)

  test("Dagger factory creates daggers with stats inside their ranges"):
    for i <- 1 to tries do
      val dagger: Dagger = daggerFactory.make()
      assertEquals(dagger.name, "Dagger")
      assert(dagger.attack >= 8 && dagger.attack <= 15)
      assert(dagger.weight >= 2 && dagger.weight <= 4)

  test("Bow factory creates bows with stats inside their ranges"):
    for i <- 1 to tries do
      val bow: Bow = bowFactory.make()
      assertEquals(bow.name, "Bow")
      assert(bow.attack >= 15 && bow.attack <= 25)
      assert(bow.weight >= 5 && bow.weight <= 8)

  test("Wand factory creates wands with stats inside their ranges"):
    for i <- 1 to tries do
      val wand: Wand = wandFactory.make()
      assertEquals(wand.name, "Wand")
      assert(wand.attack >= 2 && wand.attack <= 6)
      assert(wand.weight >= 1 && wand.weight <= 3)
      assert(wand.magicPower >= 20 && wand.magicPower <= 35)

  test("Staff factory creates staffs with stats inside their ranges"):
    for i <- 1 to tries do
      val staff: Staff = staffFactory.make()
      assertEquals(staff.name, "Staff")
      assert(staff.attack >= 8 && staff.attack <= 14)
      assert(staff.weight >= 8 && staff.weight <= 12)
      assert(staff.magicPower >= 25 && staff.magicPower <= 40)

  test("Every call creates a new weapon without owner"):
    val first = swordFactory.make()
    val second = swordFactory.make()

    assert(first != second)
    assertEquals(first.owner, None)
    assertEquals(second.owner, None)

  test("Factories with the same seed create the same stats"):
    val first = new StaffFactory(new Random(7)).make()
    val second = new StaffFactory(new Random(7)).make()

    assertEquals(first.attack, second.attack)
    assertEquals(first.weight, second.weight)
    assertEquals(first.magicPower, second.magicPower)

  test("Weapon factories can be used through their common contract"):
    val factories: List[WeaponFactory] =
      List(swordFactory, daggerFactory, bowFactory, wandFactory, staffFactory)

    val weapons = factories.map(factory => factory.make())

    assertEquals(weapons.map(weapon => weapon.name), List("Sword", "Dagger", "Bow", "Wand", "Staff"))

  test("Created weapons can be equipped following the weapon table"):
    val knight = Knight("Aragorn", 120, 20, 15)
    val blackMage = BlackMage("Saruman", 65, 6, 5, 80)
    val sword = swordFactory.make()
    val staff = staffFactory.make()

    knight.equip(sword)
    blackMage.equip(staff)

    assertEquals(knight.currentWeapon, Some(sword))
    assertEquals(blackMage.currentWeapon, Some(staff))
