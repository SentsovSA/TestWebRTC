package test.webrtc.test.ui.effect.particle

import kotlin.math.PI

object Math {
    fun toRadians(deg: Double): Double = deg / 180.0 * PI
    fun toDegrees(rad: Double): Double = rad * 180.0 / PI
}