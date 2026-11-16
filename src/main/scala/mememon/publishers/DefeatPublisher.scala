package cl.uchile.dcc
package mememon.publishers

import mememon.events.UnitDefeated
import mememon.observers.BaseSubject

/** Subject that announces the defeat of the unit that owns it. */
class DefeatPublisher extends BaseSubject[UnitDefeated]
