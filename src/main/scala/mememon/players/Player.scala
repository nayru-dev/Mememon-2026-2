package cl.uchile.dcc
package mememon.players

import mememon.units.Character

class Player(val name: String, val units: List[Character]):
  def isDefeated: Boolean=
    units.forall(unit => unit.hp <= 0)