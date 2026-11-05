package cl.uchile.dcc
package mememon.actions

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.board.NormalPanel
import mememon.exceptions.{InvalidActionException, InvalidRequirementException}
import mememon.potions.{DefensePotion, HealingPotion, MagicAttackPotion, ManaPotion}
import mememon.units.{Archer, BlackMage, Enemy, Knight, Thief, WhiteMage}
import mememon.weapons.{Bow, Dagger, Staff, Sword, Wand}

class UsableActionTest extends FunSuite:
  var knight: Knight = uninitialized
  var archer: Archer = uninitialized
  var thief: Thief = uninitialized
  var blackMage: BlackMage = uninitialized
  var whiteMage: WhiteMage = uninitialized
  var enemy: Enemy = uninitialized
  var sword: Sword = uninitialized
  var dagger: Dagger = uninitialized
  var bow: Bow = uninitialized
  var wand: Wand = uninitialized
  var staff: Staff = uninitialized
  var healing: HealingPotion = uninitialized
  var fortress: DefensePotion = uninitialized
  var manaPotion: ManaPotion = uninitialized
  var magicPotion: MagicAttackPotion = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    knight = Knight("Aragorn", 120, 20, 15)
    archer = Archer("Legolas", 90, 10, 10)
    thief = Thief("KBarriga", 85, 12, 8)
    blackMage = BlackMage("Saruman", 65, 6, 5, 80)
    whiteMage = WhiteMage("Gandalf", 70, 8, 6, 60)
    enemy = Enemy("Orco", 80, 10, 14, 18)
    sword = Sword("Master Sword", 35, 10)
    dagger = Dagger("Athame", 5, 2)
    bow = Bow("Arco de luz", 20, 7)
    wand = Wand("Patricia", 2, 0, 100)
    staff = Staff("Bakulon", 14, 12, 25)
    healing = HealingPotion("Poción curación grande")
    fortress = DefensePotion("Poción Escudo")
    manaPotion = ManaPotion("Poción de maná pequeña")
    magicPotion = MagicAttackPotion("Poción atk mágico")

  private def equipOf(character: Knight): Action = character.findAction("Equip Weapon").get

  private def consumeOf(character: Knight): Action = character.findAction("Consume Potion").get

  test("Characters have four actions and enemies only two"):
    assertEquals(knight.actions.length, 4)
    assertEquals(archer.actions.length, 4)
    assertEquals(enemy.actions.length, 2)
    assertEquals(enemy.findAction("Equip Weapon"), None)
    assertEquals(enemy.findAction("Consume Potion"), None)

  test("Actions with usable objects know only their possible objects"):
    val equip = new EquipWeaponAction
    val consume = new ConsumePotionAction

    sword.offerTo(equip, consume)
    healing.offerTo(equip, consume)

    assertEquals(equip.name, "Equip Weapon")
    assertEquals(consume.name, "Consume Potion")
    assertEquals(equip.possibleItems, List(sword))
    assertEquals(consume.possibleItems, List(healing))

  test("A possible object can be removed from an action"):
    val equip = new EquipWeaponAction
    equip.addPossibleItem(sword)
    equip.addPossibleItem(dagger)

    equip.removePossibleItem(sword)

    assertEquals(equip.possibleItems, List(dagger))

  test("A knight equips a weapon of its inventory through doAction"):
    knight.addToInventory(sword)

    knight.doAction(equipOf(knight), sword)

    assertEquals(knight.currentWeapon, Some(sword))
    assertEquals(sword.owner, Some(knight))
    assertEquals(knight.inventory, List(sword))

  test("Every kind of character can use the equip action"):
    archer.addToInventory(bow)
    thief.addToInventory(dagger)
    blackMage.addToInventory(wand)
    whiteMage.addToInventory(staff)

    archer.doAction(archer.findAction("Equip Weapon").get, bow)
    thief.doAction(thief.findAction("Equip Weapon").get, dagger)
    blackMage.doAction(blackMage.findAction("Equip Weapon").get, wand)
    whiteMage.doAction(whiteMage.findAction("Equip Weapon").get, staff)

    assertEquals(archer.currentWeapon, Some(bow))
    assertEquals(thief.currentWeapon, Some(dagger))
    assertEquals(blackMage.currentWeapon, Some(wand))
    assertEquals(whiteMage.currentWeapon, Some(staff))

  test("A character cannot equip a weapon outside its inventory"):
    val exception = intercept[InvalidRequirementException](knight.doAction(equipOf(knight), sword))

    assertEquals(exception.getMessage, "The target is not a possible object of Equip Weapon.")
    assertEquals(knight.currentWeapon, None)

  test("The equip action still follows the weapon table"):
    knight.addToInventory(wand)

    intercept[InvalidRequirementException](knight.doAction(equipOf(knight), wand))

    assertEquals(knight.currentWeapon, None)

  test("A character cannot equip a potion or consume a weapon"):
    knight.addToInventory(sword)
    knight.addToInventory(healing)

    intercept[InvalidRequirementException](knight.doAction(equipOf(knight), healing))
    intercept[InvalidRequirementException](knight.doAction(consumeOf(knight), sword))

    assertEquals(knight.currentWeapon, None)
    assertEquals(knight.inventory, List(healing, sword))

  test("A panel or unit cannot be equipped or consumed"):
    val panel = NormalPanel(1, 1)

    intercept[InvalidRequirementException](knight.doAction(equipOf(knight), panel))
    intercept[InvalidRequirementException](knight.doAction(consumeOf(knight), enemy))

  test("Targets reject equip and consume by default"):
    val panel = NormalPanel(1, 1)

    val notEquippable = intercept[InvalidActionException](panel.receiveEquip(knight))
    val notConsumable = intercept[InvalidActionException](enemy.receiveConsume(knight))

    assertEquals(notEquippable.getMessage, "Aragorn cannot equip this target.")
    assertEquals(notConsumable.getMessage, "Aragorn cannot consume this target.")

  test("An enemy cannot perform equip or consume actions"):
    val equip = new EquipWeaponAction
    val consume = new ConsumePotionAction

    val exception = intercept[InvalidActionException](enemy.doAction(equip, sword))

    assertEquals(exception.getMessage, "Orco cannot perform Equip Weapon.")
    intercept[InvalidActionException](enemy.doAction(consume, healing))

  test("Healing potion restores 20% of the maximum hp"):
    knight.takeDamage(50)
    knight.addToInventory(healing)

    knight.doAction(consumeOf(knight), healing)

    assertEquals(knight.hp, 94)

  test("Healing does not exceed the maximum hp"):
    knight.takeDamage(10)
    knight.addToInventory(healing)

    knight.doAction(consumeOf(knight), healing)

    assertEquals(knight.hp, 120)
    assertEquals(knight.maxHp, 120)

  test("Fortress potion increases defense by 15% and reduces later damage"):
    knight.addToInventory(fortress)

    knight.doAction(consumeOf(knight), fortress)

    assertEquals(knight.defense, 23)
    val strongOrc = Enemy("Uruk-hai", 40, 5, 10, 30)
    strongOrc.attack(knight)
    assertEquals(knight.hp, 113)

  test("Mana potion restores 30% of the maximum mana without exceeding it"):
    blackMage.spendMana(50)
    blackMage.addToInventory(manaPotion)
    blackMage.addToInventory(ManaPotion("Otra poción"))

    blackMage.doAction(blackMage.findAction("Consume Potion").get, manaPotion)
    assertEquals(blackMage.mana, 54)

    blackMage.restoreManaByPercent(100)
    assertEquals(blackMage.mana, 80)
    assertEquals(blackMage.maxMana, 80)

  test("Magic attack potion prepares a boost for the next spell"):
    assertEquals(whiteMage.hasSpellBoost, false)
    whiteMage.addToInventory(magicPotion)

    whiteMage.doAction(whiteMage.findAction("Consume Potion").get, magicPotion)

    assertEquals(whiteMage.hasSpellBoost, true)

  test("A consumed potion leaves the inventory and the possible objects"):
    knight.addToInventory(healing)

    knight.doAction(consumeOf(knight), healing)

    assertEquals(knight.inventory, List.empty)
    intercept[InvalidRequirementException](knight.doAction(consumeOf(knight), healing))

  test("Magic potions fail on non magic characters and are not spent"):
    knight.addToInventory(manaPotion)
    knight.addToInventory(magicPotion)

    val noMana = intercept[InvalidRequirementException](knight.doAction(consumeOf(knight), manaPotion))
    val noSpells = intercept[InvalidRequirementException](knight.doAction(consumeOf(knight), magicPotion))

    assertEquals(noMana.getMessage, "Aragorn is not a magic character, so it has no mana.")
    assertEquals(noSpells.getMessage, "Aragorn is not a magic character, so it casts no spells.")
    assertEquals(knight.inventory, List(magicPotion, manaPotion))

  test("Spending more mana than available fails and keeps the mana"):
    val exception = intercept[InvalidRequirementException](whiteMage.spendMana(61))

    assertEquals(exception.getMessage, "Gandalf needs 61 mana but only has 60.")
    assertEquals(whiteMage.mana, 60)

  test("Negative amounts do not change hp, defense or mana"):
    knight.takeDamage(20)

    knight.heal(-10)
    knight.increaseDefense(-5)
    blackMage.spendMana(-10)
    blackMage.spendMana(10)
    blackMage.restoreManaByPercent(-50)

    assertEquals(knight.hp, 100)
    assertEquals(knight.defense, 20)
    assertEquals(blackMage.mana, 70)

  test("Negative initial mana starts at zero"):
    val tiredMage = BlackMage("Saruman", 65, 6, 5, -10)

    assertEquals(tiredMage.mana, 0)
    assertEquals(tiredMage.maxMana, 0)
