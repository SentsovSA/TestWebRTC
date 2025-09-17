package test.webrtc.test.ui.effect.particle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import test.webrtc.test.ui.theme.AppColors

data class PrecipitationsParameters(
    val particleCount: Int,
    val distancePerStep: Int,
    val minSpeed: Float,
    val maxSpeed: Float,
    val minAngle: Int,
    val maxAngle: Int,
    val shape: PrecipitationShape,
    val sourceEdge: PrecipitationSourceEdge
)

val snowParametersRandomColor = PrecipitationsParameters(
	particleCount = 50,
	distancePerStep = 1,
	minSpeed = .1f,
	maxSpeed = 5f,
	minAngle = 0,
	maxAngle = 1000,
	shape = PrecipitationShape.Circle(
		minRadius = 5,
		maxRadius = 100,
		color = { Color(randomColorInt) },
		minAlpha = .01f,
		maxAlpha = .05f
	),
	sourceEdge = PrecipitationSourceEdge.BOTTOM
)

/*val rainParameters = PrecipitationsParameters(
	particleCount = 600,
	distancePerStep = 30,
	minSpeed = 0.7f,
	maxSpeed = 1f,
	minAngle = 265,
	maxAngle = 285,
	shape = PrecipitationShape.Line(
		minStrokeWidth = 1,
		maxStrokeWidth = 3,
		minHeight = 10,
		maxHeight = 15,
		color = Color.Gray,
	),
	sourceEdge = PrecipitationSourceEdge.TOP
)*/

fun blikParameters(images: List<ImageBitmap>) = PrecipitationsParameters(
	particleCount = 30,
	distancePerStep = 1,
	minSpeed = .1f,
	maxSpeed = .5f,
	minAngle = 10,
	maxAngle = 20,
	shape = PrecipitationShape.Image(
		images = images,
		minWidth = 50,
		maxWidth = 200,
		minHeight = 50,
		maxHeight = 200,
		minAlpha = .01f,
		maxAlpha = .05f,
		colorFilter = ColorFilter.tint(Color.White),
	),
	sourceEdge = PrecipitationSourceEdge.LEFT
)

val rainParameters = PrecipitationsParameters(
	particleCount = 100,
	distancePerStep = 10,
	minSpeed = .1f,
	maxSpeed = 1f,
	minAngle = 250,
	maxAngle = 500,
	shape = PrecipitationShape.Line(
		minStrokeWidth = 1,
		maxStrokeWidth = 4,
		minHeight = 50,
		maxHeight = 200,
		color = { Color(0xFFF5F5F5) },
		minAlpha = .01f,
		maxAlpha = .5f
	),
	sourceEdge = PrecipitationSourceEdge.BOTTOM
)

val rainParametersRandomColor = with(rainParameters) {
	copy(
		shape = (this.shape as PrecipitationShape.Line).copy(
			color = { AppColors.Brand.TextAndIcons.Default },
			minAlpha = .01f,
			maxAlpha = .1f
		)
	)
}