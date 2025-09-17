package test.webrtc.test.core.utils

internal fun Double.parts() : Pair<Int, Int> {
    val it = this
    val p1 = toInt()
    return p1 to ((it - p1).toString().replace(".0","")).toInt()
}