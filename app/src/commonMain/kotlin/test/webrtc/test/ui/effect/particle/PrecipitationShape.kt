package test.webrtc.test.ui.effect.particle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap

sealed class PrecipitationShape {
	
	data class Circle(
		val minRadius: Int,
		val maxRadius: Int,
		val color: () -> Color,
		val minAlpha: Float = 1f,
		val maxAlpha: Float = 1f
	) : PrecipitationShape()
	
	data class Line(
		val minStrokeWidth: Int,
		val maxStrokeWidth: Int,
		val minHeight: Int,
		val maxHeight: Int,
		val color: () -> Color,
		val minAlpha: Float = 1f,
		val maxAlpha: Float = 1f
	) : PrecipitationShape()
	
	data class Image(
		val images: List<ImageBitmap>,
		val minWidth: Int,
		val maxWidth: Int,
		val minHeight: Int,
		val maxHeight: Int,
		val colorFilter: ColorFilter,
		var rotate: Int = 0,
		val minAlpha: Float = 1f,
		val maxAlpha: Float = 1f
	) : PrecipitationShape()
	
}