package cl.uchile.dcc
package mememon.combat

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.{Knight, Archer, Enemy}
import mememon.weapons.Sword

class CombatTurnSchedulerTest extends FunSuite:
  var scheduler: CombatTurnScheduler = uninitialized
  var knight: Knight = uninitialized
  var archer: Archer = uninitialized
  var enemy: Enemy = uninitialized
  var sword: Sword = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    scheduler = CombatTurnScheduler()
    knight = Knight("Arthur", 100, 20, 15)
    archer = Archer("Robin", 80, 10, 10)
    enemy = Enemy("Goblin", 60, 5, 12, 15)
    sword = Sword("Buster Sword", 25, 10)

  test("Scheduler starts empty"):
    assertEquals(scheduler.units, List.empty)
    assertEquals(scheduler.currentTurnUnit, None)
    assertEquals(scheduler.completedUnits, List.empty)

  test("Units can be added and removed from scheduler"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    assertEquals(scheduler.units.length, 2)
    assert(scheduler.units.contains(knight))
    assert(scheduler.units.contains(enemy))

    scheduler.removeUnit(knight)
    assertEquals(scheduler.units.length, 1)
    assert(!scheduler.units.contains(knight))

  test("Scheduler does not duplicate units when added multiple times"):
    scheduler.addUnit(knight)
    scheduler.addUnit(knight)
    assertEquals(scheduler.units.length, 1)

  test("Action bar starts at 0.0 for registered units"):
    scheduler.addUnit(knight)
    assertEquals(scheduler.currentActionBar(knight), 0.0)

  test("Scheduler calculates maximum action bar correctly"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    assertEquals(scheduler.maxActionBar(knight), 15.0)
    assertEquals(scheduler.maxActionBar(enemy), 12.0)

  test("Action bars increase simultaneously for all registered units"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(5.0)
    assertEquals(scheduler.currentActionBar(knight), 5.0)
    assertEquals(scheduler.currentActionBar(enemy), 5.0)

  test("Negative or zero increase does not alter action bars"):
    scheduler.addUnit(knight)
    scheduler.increaseActionBars(-10.0)
    assertEquals(scheduler.currentActionBar(knight), 0.0)
    scheduler.increaseActionBars(0.0)
    assertEquals(scheduler.currentActionBar(knight), 0.0)

  test("Scheduler correctly identifies completed action bars"):
    scheduler.addUnit(enemy)
    assertEquals(scheduler.hasCompletedActionBar(enemy), false)
    scheduler.increaseActionBars(10.0)
    assertEquals(scheduler.hasCompletedActionBar(enemy), false)
    scheduler.increaseActionBars(2.0)
    assertEquals(scheduler.hasCompletedActionBar(enemy), true)

  test("Action bar resets correctly for a specific unit"):
    scheduler.addUnit(knight)
    scheduler.increaseActionBars(20.0)
    assertEquals(scheduler.currentActionBar(knight), 20.0)
    scheduler.resetActionBar(knight)
    assertEquals(scheduler.currentActionBar(knight), 0.0)

  test("Completed units are ordered from highest to lowest overflow"):
    scheduler.addUnit(knight)
    scheduler.addUnit(archer)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(20.0)
    val completed = scheduler.completedUnits
    assertEquals(completed, List(archer, enemy, knight))

  test("Current turn unit points to the single unit with highest overflow"):
    scheduler.addUnit(knight)
    scheduler.addUnit(archer)
    scheduler.increaseActionBars(20.0)
    assertEquals(scheduler.currentTurnUnit, Some(archer))

  test("Current turn returns None when no unit has completed its action bar"):
    scheduler.addUnit(knight)
    scheduler.increaseActionBars(5.0)
    assertEquals(scheduler.currentTurnUnit, None)