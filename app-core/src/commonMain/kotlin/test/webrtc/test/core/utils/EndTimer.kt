package test.webrtc.test.core.utils

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Instant

internal class EndTimer<T> : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val items = mutableMapOf<T, String>()
    private val callbacks = mutableSetOf<Callback<T>>()
    private val mutex = Mutex(locked = true)

    init {
        launch {
            while (isActive) {
                mutex.withLock {
                    val currentTime = currentTime.toEpochMilliseconds()
                    val current = Instant.fromEpochMilliseconds(currentTime)
                    val iterator = this@EndTimer.items.iterator()
                    while (iterator.hasNext()) {
                        val entry = iterator.next()
                        val value = entry.key
                        val targetTimeInMillis = entry.value.toLong()
                        val target = Instant.fromEpochMilliseconds(targetTimeInMillis)
                        val duration = target - current
                        if (duration.isPositive()) {
                            duration.toComponents { days, hours, minutes, seconds, nanoseconds ->
                                for (callback in callbacks) {
                                    callback.onEndTimer(
                                        value = value,
                                        days = days,
                                        hours = hours,
                                        minutes = minutes,
                                        seconds = seconds,
                                        nanoseconds = nanoseconds
                                    )
                                }
                                /*Napier.d("[EndTimer] value: $value, days: $days, hours: $hours, minutes: $minutes, seconds: $seconds, nanoseconds: $nanoseconds")*/
                            }
                        } else {
                            iterator.remove()
                        }
                    }
                    if (items.isEmpty()) {
                        Napier.d("[EndTimer] LOCKED")
                        mutex.lock()
                    }
                }
                /*delay(100)*/
            }
        }
    }

    fun registerCallback(callback: Callback<T>) {
        callbacks.add(callback)
    }

    fun unregisterCallback(callback: Callback<T>) {
        callbacks.remove(callback)
    }

    fun add(value: T, targetTimeInMillis: String) {
        items[value] = targetTimeInMillis
        if (mutex.isLocked) mutex.unlock()
    }

    fun clear() {
        items.clear()
    }

    interface Callback<T> {
        suspend fun onEndTimer(
            value: T,
            days: Long,
            hours: Int,
            minutes: Int,
            seconds: Int,
            nanoseconds: Int
        )
    }

}