package cl.uchile.dcc
package mememon.factories.units

import munit.FunSuite
import scala.compiletime.uninitialized
import scala.util.Random
import mememon.units.{Character, Enemy, Knight}

class UnitFactoryTest extends FunSuite:
  val tries: Int = 50
  val basicPotions: List[String] = List("Healing Potion", "Defense Potion")
  val allPotions: List[String] = "Mana Potion" :: "Magic Attack Potion" :: basicPotions
  var random: Random = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    random = new Random(42)

  private def checkStats(character: Character, minHp: Int, maxHp: Int, minDefense: Int, maxDefense: Int, minWeight: Int, maxWeight: Int): Unit =
    assert(character.hp >= minHp && character.hp <= maxHp)
    assertEquals(character.maxHp, character.hp)
    assert(character.defense >= minDefense && character.defense <= maxDefense)
    assert(character.weight >= minWeight && character.weight <= maxWeight)

  private def checkResources(character: Character, weapons: List[String], potions: List[String]): Unit =
    val weapon = character.currentWeapon.get
    assert(weapons.contains(weapon.name))
    assertEquals(weapon.owner, Some(character))
    assert(character.inventory.contains(weapon))
    val potionNames = character.inventory.filter(item => item != weapon).map(item => item.name)
    assert(potionNames.length >= 1 && potionNames.length <= 3)
    assert(potionNames.forall(name => potions.contains(name)))

  test("Knight factory creates armed knights with basic potions"):
    val factory = new KnightFactory(random)
    for i <- 1 to tries do
      val knight = factory.make()
      assertEquals(knight.name, "Knight")
      checkStats(knight, 100, 130, 15, 25, 12, 18)
      checkResources(knight, List("Sword", "Dagger", "Bow"), basicPotions)

  test("Archer factory creates armed archers with basic potions"):
    val factory = new ArcherFactory(random)
    for i <- 1 to tries do
      val archer = factory.make()
      assertEquals(archer.name, "Archer")
      checkStats(archer, 80, 100, 8, 14, 8, 12)
      checkResources(archer, List("Sword", "Bow", "Wand"), basicPotions)

  test("Thief factory creates armed thieves with basic potions"):
    val factory = new ThiefFactory(random)
    for i <- 1 to tries do
      val thief = factory.make()
      assertEquals(thief.name, "Thief")
      checkStats(thief, 75, 95, 8, 14, 6, 10)
      checkResources(thief, List("Sword", "Dagger"), basicPotions)

  test("Black mage factory creates mages with magic weapons and any potion"):
    val factory = new BlackMageFactory(random)
    for i <- 1 to tries do
      val mage = factory.make()
      assertEquals(mage.name, "Black Mage")
      checkStats(mage, 60, 80, 5, 10, 4, 8)
      checkResources(mage, List("Wand", "Staff"), allPotions)

  test("White mage factory creates mages with magic weapons and any potion"):
    val factory = new WhiteMageFactory(random)
    for i <- 1 to tries do
      val mage = factory.make()
      assertEquals(mage.name, "White Mage")
      checkStats(mage, 60, 80, 5, 10, 4, 8)
      checkResources(mage, List("Wand", "Staff"), allPotions)

  test("Mage factories give enough mana to cast their spells"):
    val enemy = Enemy("Orco", 500, 0, 14, 18)
    val knight = Knight("Aragorn", 120, 20, 15)
    knight.takeDamage(60)
    val blackMage = new BlackMageFactory(random).make()
    val whiteMage = new WhiteMageFactory(random).make()

    blackMage.doAction(blackMage.findAction("Thunder").get, enemy)
    whiteMage.doAction(whiteMage.findAction("Healing").get, knight)

    assert(enemy.hp < 500)
    assert(knight.hp > 60)

  test("Enemy factory creates enemies with stats inside their ranges"):
    val factory = new EnemyFactory(random)
    for i <- 1 to tries do
      val enemy: Enemy = factory.make()
      assertEquals(enemy.name, "Enemy")
      assert(enemy.hp >= 70 && enemy.hp <= 110)
      assert(enemy.defense >= 8 && enemy.defense <= 15)
      assert(enemy.weight >= 10 && enemy.weight <= 18)
      assert(enemy.attackDamage >= 12 && enemy.attackDamage <= 25)

  test("Created units can fight each other"):
    val knight = new KnightFactory(random).make()
    val enemy = new EnemyFactory(random).make()

    knight.doAction(knight.findAction("Attack").get, enemy)

    assert(enemy.hp < enemy.maxHp)

  test("Every call creates a new unit"):
    val factory = new KnightFactory(random)

    assert(factory.make() != factory.make())

  test("Factories with the same seed create the same units"):
    val first = new ThiefFactory(new Random(7)).make()
    val second = new ThiefFactory(new Random(7)).make()

    assertEquals(first.hp, second.hp)
    assertEquals(first.defense, second.defense)
    assertEquals(first.currentWeapon.get.attack, second.currentWeapon.get.attack)
    assertEquals(first.inventory.length, second.inventory.length)

  test("Unit factories can be used through their common contract"):
    val factories: List[UnitFactory] = List(
      new KnightFactory(random), new ArcherFactory(random), new ThiefFactory(random),
      new BlackMageFactory(random), new WhiteMageFactory(random), new EnemyFactory(random)
    )

    val units = factories.map(factory => factory.make())

    assertEquals(
      units.map(unit => unit.name),
      List("Knight", "Archer", "Thief", "Black Mage", "White Mage", "Enemy")
    )
