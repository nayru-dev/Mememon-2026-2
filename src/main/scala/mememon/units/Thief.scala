package cl.uchile.dcc
package mememon.units

/** Represents a Thief physical unit specialized in agility.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Thief(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)
