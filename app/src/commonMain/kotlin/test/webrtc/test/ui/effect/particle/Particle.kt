package test.webrtc.test.ui.effect.particle

import androidx.compose.ui.graphics.Color

data class Particle(
	var n: Long = 0,
	var x: Float,
	var y: Float,
	var width: Float,
	var height: Float,
	var color: Color?,
	var alpha: Float,
	var speed: Float,
	var angle: Int
)