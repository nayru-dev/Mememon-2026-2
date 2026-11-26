package cl.uchile.dcc
package mememon.factories.potions

/** Abstract base for potion factories, sharing the name given to every created potion.
 *
 * @param potionName name given to the potions this factory creates
 */
abstract class AbstractPotionFactory(protected val potionName: String) extends PotionFactory
