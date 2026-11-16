package cl.uchile.dcc
package mememon.observers

import scala.collection.mutable

/** Reusable implementation of a [[Subject]].
 *
 * The constructor is protected, so it can only be extended by concrete subjects.
 * Observers are kept in insertion order and without duplicates.
 *
 * @tparam T type of the notification
 */
class BaseSubject[T] protected extends Subject[T]:
  private val observers: mutable.Set[Observer[T]] =
    mutable.LinkedHashSet.empty[Observer[T]]

  /** @inheritdoc */
  override def attach(observer: Observer[T]): Unit =
    observers += observer

  /** @inheritdoc */
  override def detach(observer: Observer[T]): Unit =
    observers -= observer

  /** @inheritdoc */
  override def notifyObservers(notification: T): Unit =
    for observer <- observers do observer.update(this, notification)
