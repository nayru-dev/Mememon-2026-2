package cl.uchile.dcc
package mememon.observers

import mememon.events.UnitDefeated
import mememon.units.GameUnit
import scala.collection.mutable.ArrayBuffer

/** Test observer that records every defeat notification it receives. */
class DefeatRecorder extends Observer[UnitDefeated]:
  private val _subjects: ArrayBuffer[Subject[UnitDefeated]] = ArrayBuffer.empty[Subject[UnitDefeated]]
  private val _defeated: ArrayBuffer[GameUnit] = ArrayBuffer.empty[GameUnit]

  /** @inheritdoc */
  def update(subject: Subject[UnitDefeated], response: UnitDefeated): Unit =
    _subjects += subject
    _defeated += response.unit

  /** Subjects that sent a notification, in order.
   *
   * @return the notifying subjects
   */
  def subjects: List[Subject[UnitDefeated]] = _subjects.toList

  /** Units reported as defeated, in order.
   *
   * @return the defeated units
   */
  def defeated: List[GameUnit] = _defeated.toList
