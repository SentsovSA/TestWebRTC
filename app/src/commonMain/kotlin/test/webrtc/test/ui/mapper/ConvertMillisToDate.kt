package test.webrtc.test.ui.mapper

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun convertMillisToDateFront(millis: Long): String {
    val formatter = millis.toLocalDateTime
    return "${formatter.dayOfMonth} ${months[formatter.monthNumber - 1]}, ${formatter.year}"
}

fun convertMillisToDateBack(millis: Long): String {
    val formatter = millis.toLocalDateTime
    return "${formatter.year}-${formatter.monthNumber.toString().padStart(2, '0')}-${formatter.dayOfMonth.toString().padStart(2, '0')}"
}

internal val Long.toLocalDateTime get() = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault())

internal val months = arrayOf(
    "января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря"
)