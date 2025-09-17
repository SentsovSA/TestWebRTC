package test.webrtc.test.core.utils

internal actual fun decimalFormat(double: Double): String {
    val df = java.text.DecimalFormat()
    df.isGroupingUsed = false
    df.maximumFractionDigits = 2
    df.isDecimalSeparatorAlwaysShown = false
    return df.format(double)
}