package test.webrtc.test.ui.effect.particle

import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleSystemHelper(
	private val parameters: PrecipitationsParameters,
	private val frameWidth: Int,
	private val frameHeight: Int
) {

	private var iteration = 0L

	fun generateParticles() = (1..parameters.particleCount).map {
		val color = getRandomColor()
		val randomAlpha = getRandomAlpha()
		val randomWidth = getRandomWidth()
		val randomHeight = getRandomHeight(randomWidth)
		val angle = computeAngle()
		Particle(
			x = generateX(),
			y = generateY(),
			width = randomWidth,
			height = randomHeight,
			speed = Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed,
			angle = angle,
			color = color,
			alpha = randomAlpha
		)
	}

	private fun generateX(startFromSourceEdge: Boolean = false): Float {
		val randomX = Random.nextInt(frameWidth).toFloat()
		return when (parameters.sourceEdge) {
			PrecipitationSourceEdge.TOP -> randomX
			PrecipitationSourceEdge.RIGHT -> if (startFromSourceEdge) frameWidth.toFloat() else randomX
			PrecipitationSourceEdge.BOTTOM -> randomX
			PrecipitationSourceEdge.LEFT -> if (startFromSourceEdge) 0f else randomX
		}
	}

	private fun generateY(startFromSourceEdge: Boolean = false): Float {
		val randomY = Random.nextInt(frameHeight).toFloat()
		return when (parameters.sourceEdge) {
			PrecipitationSourceEdge.TOP -> if (startFromSourceEdge) 0f else randomY
			PrecipitationSourceEdge.RIGHT -> randomY
			PrecipitationSourceEdge.BOTTOM -> if (startFromSourceEdge) frameHeight.toFloat() else randomY
			PrecipitationSourceEdge.LEFT -> randomY
		}
	}

	private fun isOutOfFrame(particle: Particle): Boolean = when (parameters.sourceEdge) {
		PrecipitationSourceEdge.TOP ->
			particle.y > frameHeight || particle.x < 0 || particle.x > frameWidth
		PrecipitationSourceEdge.RIGHT ->
			particle.y - particle.height > frameHeight ||
			particle.y + particle.height < 0 ||
			particle.x + particle.width < 0
		PrecipitationSourceEdge.BOTTOM ->
			particle.y < 0 || particle.x < 0 || particle.x > frameWidth
		PrecipitationSourceEdge.LEFT -> {
			particle.y - particle.height > frameHeight ||
			particle.y + particle.height < 0 ||
			particle.x - particle.width > frameWidth
		}
	}

	fun updateParticles(particles: List<Particle>) = particles.map { particle ->
		val copy = particle.copy(
			n = iteration++
		)
		if (isOutOfFrame(particle)) {
			val randomAlpha = getRandomAlpha()
			val randomWidth = getRandomWidth()
			val randomHeight = getRandomHeight(randomWidth)
			val angle = computeAngle()
			copy.copy(
				width = randomWidth,
				height = randomHeight,
				alpha = randomAlpha,
				x = generateX(startFromSourceEdge = true),
				y = generateY(startFromSourceEdge = true),
				speed = Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed,
				angle = angle
			)
		} else {
			copy.copy(
				x = particle.x - (parameters.distancePerStep * particle.speed) *
						cos(Math.toRadians(particle.angle.toDouble())).toFloat(),
				y = particle.y - (parameters.distancePerStep * particle.speed) *
						sin(Math.toRadians(particle.angle.toDouble())).toFloat()
			)
		}
	}

	private fun getRandomAlpha(): Float = when (parameters.shape) {
		is PrecipitationShape.Circle -> Random.nextFloat() *
				(parameters.shape.maxAlpha - parameters.shape.minAlpha) + parameters.shape.minAlpha
		is PrecipitationShape.Line -> Random.nextFloat() *
				(parameters.shape.maxAlpha - parameters.shape.minAlpha) + parameters.shape.minAlpha
		is PrecipitationShape.Image -> Random.nextFloat() *
				(parameters.shape.maxAlpha - parameters.shape.minAlpha) + parameters.shape.minAlpha
	}

	private fun getRandomColor() : Color? = when (parameters.shape) {
		is PrecipitationShape.Circle -> parameters.shape.color()
		is PrecipitationShape.Line -> parameters.shape.color()
		else -> null
	}

	private fun getRandomWidth(): Float {
		return when (parameters.shape) {
			is PrecipitationShape.Circle -> Random.nextInt(
				parameters.shape.minRadius,
				parameters.shape.maxRadius
			).toFloat()
			is PrecipitationShape.Line -> Random.nextInt(
				parameters.shape.minStrokeWidth,
				parameters.shape.maxStrokeWidth
			).toFloat()
			is PrecipitationShape.Image -> Random.nextInt(
				parameters.shape.minWidth,
				parameters.shape.maxWidth
			).toFloat()
		}
	}

	private fun getRandomHeight(width: Float): Float {
		return when (parameters.shape) {
			is PrecipitationShape.Circle -> width
			is PrecipitationShape.Line -> Random.nextInt(
				parameters.shape.minHeight,
				parameters.shape.maxHeight
			).toFloat()
			is PrecipitationShape.Image -> Random.nextInt(
				parameters.shape.minHeight,
				parameters.shape.maxHeight
			).toFloat()
		}
	}

	private fun computeAngle(): Int {
		return if (parameters.minAngle == parameters.maxAngle) {
			parameters.maxAngle
		} else {
			Random.nextInt(parameters.minAngle, parameters.maxAngle)
		}
	}
}