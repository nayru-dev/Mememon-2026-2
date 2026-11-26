package cl.uchile.dcc
package mememon.actions

import mememon.exceptions.InvalidRequirementException
import mememon.items.Usable
import mememon.units.Character

/** Abstract base for actions that use one of a list of possible usable objects.
 *
 * @param name display name of the action
 */
abstract class AbstractUsableAction(name: String) extends AbstractCharacterAction(name):
  private var _possibleItems: List[Usable] = List.empty

  /** Returns the objects this action can currently use.
   *
   * @return the possible objects
   */
  def possibleItems: List[Usable] = _possibleItems

  /** Registers an object this action can use.
   *
   * @param item the object to register
   */
  def addPossibleItem(item: Usable): Unit =
    _possibleItems = item :: _possibleItems

  /** Removes an object from the possible ones. It has no effect if the object is not registered.
   *
   * @param item the object to remove
   */
  def removePossibleItem(item: Usable): Unit =
    _possibleItems = _possibleItems.filter(current => current != item)

  /** @inheritdoc */
  protected def applyTo(source: Character, target: Target): Unit =
    if !_possibleItems.contains(target) then
      throw new InvalidRequirementException(s"The target is not a possible object of $name.")
    useOn(source, target)

  /** Uses the chosen object, asking it to receive this action.
   *
   * @param source the character using the object
   * @param target the chosen object
   */
  protected def useOn(source: Character, target: Target): Unit
