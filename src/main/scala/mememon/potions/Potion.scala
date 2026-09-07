package cl.uchile.dcc
package mememon.potions

trait Potion:
  val name: String

abstract class AbstractPotion(val name: String)
  extends Potion

class HealingPotion(name: String) extends AbstractPotion(name)
class DefensePotion(name: String) extends AbstractPotion(name)
class ManaPotion(name: String) extends AbstractPotion(name)
class MagicAttackPotion(name: String) extends AbstractPotion(name)

