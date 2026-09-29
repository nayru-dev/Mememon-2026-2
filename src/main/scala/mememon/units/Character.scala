package cl.uchile.dcc
package mememon.units

import mememon.weapons.Weapon

/** Contrato base que define el comportamiento y atributos comunes de toda unidad en el juego. */
trait Character:
  /** Nombre representativo de la unidad. */
  val name: String

  /** Puntos de vida actuales de la unidad. */
  def hp: Int

  /** Puntos de defensa fisica de la unidad. */
  val defense: Int

  /** Peso base de la unidad. */
  val weight: Int

  /** Arma actualmente equipada por la unidad, si posee alguna. */
  def currentWeapon: Option[Weapon]

  /** Retorna el dano de ataque fisico total de la unidad. */
  def attackDamage: Int

  /** Descuenta una cantidad de daño a los puntos de vida de la unidad, sin bajar de cero. */
  def takeDamage(amount: Int): Unit

  /** Ejecuta una accion de ataque fisico contra una unidad objetivo. */
  def attack(target: Character): Unit

  /** Calcula el valor maximo de la barra de accion de la unidad para el sistema de turnos. */
  def maxActionBar: Double

/** Contrato para las unidades capaces de canalizar puntos de magia. */
trait MagicCharacter extends Character:
  /** Puntos de mana maximos o actuales del personaje magico. */
  val mana: Int

/** Implementacion abstracta que agrupa la logica compartida de los personajes jugables. */
abstract class AbstractCharacter(
                                  val name: String,
                                  initialHp: Int,
                                  val defense: Int,
                                  val weight: Int
                                ) extends Character:
  private var _hp: Int = math.max(0, initialHp)
  private var _currentWeapon: Option[Weapon] = None
  private var _inventory: List[Any] = List.empty

  def hp: Int = _hp

  def currentWeapon: Option[Weapon] = _currentWeapon

  def inventory: List[Any] = _inventory

  def attackDamage: Int =
    _currentWeapon match
      case Some(w) => w.attack
      case None    => 0

  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  def attack(target: Character): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  def maxActionBar: Double =
    val weaponWeight = _currentWeapon match
      case Some(w) => w.weight.toDouble
      case None    => 0.0
    weight.toDouble + 0.5 * weaponWeight

/** Molde abstracto para personajes que utilizan mana. */
abstract class AbstractMagicCharacter(
                                       name: String,
                                       initialHp: Int,
                                       defense: Int,
                                       weight: Int,
                                       val mana: Int
                                     ) extends AbstractCharacter(name, initialHp, defense, weight)
  with MagicCharacter

/** Representa a la unidad fisica Caballero. */
class Knight(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Representa a la unidad fisica Arquero. */
class Archer(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Representa a la unidad fisica Ladron. */
class Thief(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)

/** Representa a la unidad magica Mago Blanco. */
class WhiteMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana)

/** Representa a la unidad magica Mago Negro. */
class BlackMage(name: String, initialHp: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, initialHp, defense, weight, mana)

/** Representa a una criatura enemiga no jugable en el campo de batalla. */
class Enemy(
             val name: String,
             initialHp: Int,
             val defense: Int,
             val weight: Int,
             val attack: Int
           ) extends Character:
  private var _hp: Int = math.max(0, initialHp)

  def currentWeapon: Option[Weapon] = None
  def hp: Int = _hp
  def attackDamage: Int = attack

  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  def attack(target: Character): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  def maxActionBar: Double = weight.toDouble