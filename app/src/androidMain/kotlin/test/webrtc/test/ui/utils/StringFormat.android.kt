package test.webrtc.test.ui.utils

import kotlin.text.format as ktformat
/*fun Float.roundTo(n : Int) : Float {
    return "%.${n}f".format(this).toFloat()
}

fun Double.roundTo(n : Int) : Double {
    return
}*/

actual fun String.format(vararg args: Any?) : String = this.ktformat(*args)