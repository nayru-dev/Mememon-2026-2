package cl.uchile.dcc
package mememon.observers

import munit.FunSuite
import scala.compiletime.uninitialized
import mememon.units.{Enemy, Knight}

class DefeatNotificationTest extends FunSuite:
  var aragorn: Knight = uninitialized
  var orco: Enemy = uninitialized
  var recorder: DefeatRecorder = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    aragorn = Knight("Aragorn", 100, 20, 10)
    orco = Enemy("Orco", 80, 10, 14, 18)
    recorder = DefeatRecorder()

  test("A unit's publisher notifies its observer when the unit is defeated"):
    aragorn.defeatPublisher.attach(recorder)
    aragorn.takeDamage(100)
    assertEquals(recorder.defeated, List(aragorn))
    assertEquals(recorder.subjects, List(aragorn.defeatPublisher))

  test("An enemy also notifies its observer when defeated"):
    orco.defeatPublisher.attach(recorder)
    orco.takeDamage(500)
    assertEquals(recorder.defeated, List(orco))

  test("Non-lethal damage does not notify"):
    aragorn.defeatPublisher.attach(recorder)
    aragorn.takeDamage(99)
    assertEquals(recorder.defeated, List())

  test("A defeated unit notifies only once"):
    aragorn.defeatPublisher.attach(recorder)
    aragorn.takeDamage(100)
    aragorn.takeDamage(10)
    assertEquals(recorder.defeated, List(aragorn))

  test("Attaching the same observer twice notifies it only once"):
    aragorn.defeatPublisher.attach(recorder)
    aragorn.defeatPublisher.attach(recorder)
    aragorn.takeDamage(100)
    assertEquals(recorder.defeated, List(aragorn))

  test("A detached observer is not notified"):
    aragorn.defeatPublisher.attach(recorder)
    aragorn.defeatPublisher.detach(recorder)
    aragorn.takeDamage(100)
    assertEquals(recorder.defeated, List())

  test("Every attached observer is notified"):
    val otherRecorder = DefeatRecorder()
    orco.defeatPublisher.attach(recorder)
    orco.defeatPublisher.attach(otherRecorder)
    orco.takeDamage(80)
    assertEquals(recorder.defeated, List(orco))
    assertEquals(otherRecorder.defeated, List(orco))

  test("Each unit has its own publisher"):
    aragorn.defeatPublisher.attach(recorder)
    orco.takeDamage(80)
    assertEquals(recorder.defeated, List())
    assert(aragorn.defeatPublisher != orco.defeatPublisher)

  test("A unit without observers can still be defeated"):
    aragorn.takeDamage(100)
    assertEquals(aragorn.hp, 0)
