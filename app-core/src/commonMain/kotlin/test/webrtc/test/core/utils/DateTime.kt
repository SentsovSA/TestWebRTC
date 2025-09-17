package test.webrtc.test.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

internal val currentTime get() : Instant = Clock.System.now()
internal val currentDateTime get() : LocalDateTime =
    currentTime.toLocalDateTime(TimeZone.currentSystemDefault())

internal val Long.toLocalDateTime get() = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

internal fun Long.format(pattern: DatePattern) = toLocalDateTime
    .format(LocalDateTime.Format { byUnicodePattern(pattern.value) })

enum class DatePattern(
    val value: String
) {
    ddMMyyyyHHmm("dd.MM.yyyy, HH:mm"),
    HHmm("HH:mm")
}