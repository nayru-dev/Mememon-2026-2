package cl.uchile.dcc
package mememon.units

/** Represents a Knight physical unit specialized in defense.
 *
 * @param name unit name
 * @param initialHp starting health points
 * @param defense defensive threshold
 * @param weight character weight
 */
class Knight(name: String, initialHp: Int, defense: Int, weight: Int)
  extends AbstractCharacter(name, initialHp, defense, weight)
