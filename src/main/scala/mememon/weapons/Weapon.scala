package cl.uchile.dcc
package mememon.weapons

trait Weapon:
  val name: String
  val attack: Int
  val weight: Int

trait MagicWeapon extends Weapon:
  val magicAttack: Int

abstract class AbstractWeapon(val name: String, val attack: Int, val weight: Int)
  extends Weapon

abstract class AbstractMagicWeapon(name: String, attack: Int, weight: Int, val magicAttack: Int)
  extends AbstractWeapon(name, attack, weight)
  with MagicWeapon

class Sword(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

class Dagger(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

class Bow(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

class Wand(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack)

class Staff(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack)

