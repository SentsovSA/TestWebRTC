package test.webrtc.test.ui.effect.particle

import kotlin.random.Random

val randomColorInt get() = (Random.nextDouble() * 16777215).toInt() or (0xFF shl 24)