package cl.uchile.dcc
package mememon.weapons

/** Base contract representing any equipable weapon item in the game. */
trait Weapon:
  /** Unique display name of the weapon. */
  val name: String

  /** Physical attack power contributed by the weapon. */
  val attack: Int

  /** Weight of the weapon affecting the holder's action bar speed. */
  val weight: Int

/** Contract representing weapons capable of channeling magical damage. */
trait MagicWeapon extends Weapon:
  /** Magical attack power provided by this weapon. */
  val magicAttack: Int

/** Abstract base implementation for physical weapons.
 *
 * @param name unique name of the weapon
 * @param attack physical attack power
 * @param weight weight threshold of the weapon
 */
abstract class AbstractWeapon(val name: String, val attack: Int, val weight: Int)
  extends Weapon

/** Abstract base implementation for weapons possessing magical damage.
 *
 * @param name unique name of the weapon
 * @param attack physical attack power
 * @param weight weight threshold of the weapon
 * @param magicAttack magical damage capability
 */
abstract class AbstractMagicWeapon(
                                    name: String,
                                    attack: Int,
                                    weight: Int,
                                    val magicAttack: Int
                                  ) extends AbstractWeapon(name, attack, weight)
  with MagicWeapon

/** Represents a standard Sword used for physical melee attacks.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Sword(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

/** Represents a lightweight Dagger weapon.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Dagger(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

/** Represents a Bow used for physical ranged combat.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 */
class Bow(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)

/** Represents a Wand capable of casting spells.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 * @param magicAttack magical damage capability
 */
class Wand(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack)

/** Represents a heavy Staff designed for high-potency magical attacks.
 *
 * @param name weapon name
 * @param attack physical damage value
 * @param weight weight value
 * @param magicAttack magical damage capability
 */
class Staff(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack)