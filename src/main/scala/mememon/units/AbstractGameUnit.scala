package cl.uchile.dcc
package mememon.units

import mememon.actions.{AbstractTarget, Action, AttackAction, MoveAction}
import mememon.board.Panel

/** Abstract implementation of the state and behavior shared by characters and enemies.
 *
 * @param name identifier name of the unit
 * @param initialHp initial health points before combat
 * @param initialDefense initial physical defense value
 * @param weight base weight of the unit
 */
abstract class AbstractGameUnit(
                                 val name: String,
                                 initialHp: Int,
                                 initialDefense: Int,
                                 val weight: Int
                               ) extends AbstractTarget with GameUnit:
  private val _maxHp: Int = math.max(0, initialHp)
  private var _hp: Int = _maxHp
  private var _defense: Int = initialDefense
  private var _currentPanel: Option[Panel] = None
  private val _actions: List[Action] = List(new AttackAction, new MoveAction)

  /** @inheritdoc */
  def actions: List[Action] = _actions

  /** @inheritdoc */
  def findAction(actionName: String): Option[Action] =
    val matches = actions.filter(action => action.name == actionName)
    if matches.isEmpty then None else Some(matches.head)

  /** @inheritdoc */
  def currentPanel: Option[Panel] = _currentPanel

  /** @inheritdoc */
  def setCurrentPanel(panel: Option[Panel]): Unit =
    _currentPanel = panel

  /** @inheritdoc */
  def hp: Int = _hp

  /** @inheritdoc */
  def maxHp: Int = _maxHp

  /** @inheritdoc */
  def heal(amount: Int): Unit =
    val actualHealing = math.max(0, amount)
    _hp = math.min(_maxHp, _hp + actualHealing)

  /** @inheritdoc */
  def defense: Int = _defense

  /** @inheritdoc */
  def increaseDefense(amount: Int): Unit =
    _defense = _defense + math.max(0, amount)

  /** @inheritdoc */
  def takeDamage(amount: Int): Unit =
    val actualDamage = math.max(0, amount)
    _hp = math.max(0, _hp - actualDamage)

  /** @inheritdoc */
  def attack(target: GameUnit): Unit =
    val damage = math.max(0, this.attackDamage - target.defense)
    target.takeDamage(damage)

  /** @inheritdoc */
  override def receiveAttack(source: GameUnit): Unit =
    source.attack(this)
