package test.webrtc.test.core.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

internal actual fun decimalFormat(double: Double): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 0u
    formatter.maximumFractionDigits = 2u
    formatter.numberStyle = 1u //Decimal
    return formatter.stringFromNumber(NSNumber(double))!!
}