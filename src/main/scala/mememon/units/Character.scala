package cl.uchile.dcc
package mememon.units
import mememon.weapons.Weapon

trait Character:
  val name: String
  val hp: Int
  val defense: Int
  val weight: Int

trait MagicCharacter extends Character:
  val mana: Int

abstract class AbstractCharacter(val name: String, val hp: Int, val defense: Int, val weight: Int)
  extends Character:
  var currentWeapon: Option[Weapon]=None
  var inventory: List[Any]=List.empty

abstract class AbstractMagicCharacter(name: String, hp: Int, defense: Int, weight: Int, val mana: Int)
  extends AbstractCharacter(name, hp, defense, weight)
  with MagicCharacter

class Knight(name: String, hp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, hp, defense, weight)

class Archer(name: String, hp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, hp, defense, weight)

class Thief(name: String, hp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, hp, defense, weight)

class WhiteMage(name: String, hp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, hp, defense, weight, mana)

class BlackMage(name: String, hp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, hp, defense, weight, mana)

class Enemy(val name: String, val hp: Int, val defense: Int, val weight: Int, val attack: Int)