package cl.uchile.dcc
package mememon.events

import mememon.units.GameUnit

/** Event sent when a unit's health points drop to zero.
 *
 * @param unit the unit that was defeated
 */
class UnitDefeated(val unit: GameUnit)
