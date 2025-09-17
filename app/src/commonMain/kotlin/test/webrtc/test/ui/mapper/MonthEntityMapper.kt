package test.webrtc.test.ui.mapper

import kotlinx.datetime.Month

fun Month.toStringResource() = when(this) {
    Month.JANUARY -> "Января"
    Month.FEBRUARY -> "Февраля"
    Month.MARCH -> "Марта"
    Month.APRIL -> "Апреля"
    Month.MAY -> "Мая"
    Month.JUNE -> "Июня"
    Month.JULY -> "Июля"
    Month.AUGUST -> "Августа"
    Month.SEPTEMBER -> "Сентября"
    Month.OCTOBER -> "Октября"
    Month.NOVEMBER -> "Ноября"
    Month.DECEMBER -> "Декабря"
    else -> "Какого-то месяца"
}