package cl.uchile.dcc
package mememon.observers

/** Contract for objects that notify their attached [[Observer]]s.
 *
 * @tparam T type of the notification
 */
trait Subject[T]:
  /** Subscribes an observer to the notifications of this subject.
   *
   * @param observer the observer to attach
   */
  def attach(observer: Observer[T]): Unit

  /** Unsubscribes an observer, so it stops receiving notifications.
   *
   * @param observer the observer to detach
   */
  def detach(observer: Observer[T]): Unit

  /** Sends a notification to every attached observer.
   *
   * @param notification the notification to send
   */
  def notifyObservers(notification: T): Unit
