package test.webrtc.test.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.defaultShimmerTheme

val shimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            800,
            easing = LinearEasing,
            delayMillis = 500
        ),
        repeatMode = RepeatMode.Restart,
    ),
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = .1f),
        Color.Unspecified.copy(alpha = .3f),
        Color.Unspecified.copy(alpha = .1f)
    )
)