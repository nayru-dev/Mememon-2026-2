package cl.uchile.dcc
package mememon.observers

/** Contract for objects that react when a [[Subject]] they are attached to notifies them.
 *
 * @tparam T type of the notification
 */
trait Observer[T]:
  /** Reacts to a notification sent by a subject.
   *
   * @param subject the subject that sent the notification
   * @param response the notification sent
   */
  def update(subject: Subject[T], response: T): Unit
