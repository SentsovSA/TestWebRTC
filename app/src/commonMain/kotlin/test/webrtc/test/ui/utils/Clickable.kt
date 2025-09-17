package test.webrtc.test.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalViewConfiguration
import test.webrtc.test.context.LocalPlatformContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Modifier.click(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
): Modifier = composed {
    val context = LocalPlatformContext.current
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    val viewConfig = LocalViewConfiguration.current
    val longPressTimeout = viewConfig.longPressTimeoutMillis

    val coroutineScope = rememberCoroutineScope()

    val pressGestureModifier = if (enabled) {
        Modifier.pointerInput(onClick, onLongClick) {
            detectTapGestures(
                onPress = { offset ->
                    val press = PressInteraction.Press(offset)
                    interactionSource.emit(press)
                    isPressed = true

                    var longPressTriggered = false

                    val job = coroutineScope.launch {
                        delay(longPressTimeout.toLong())
                        onLongClick?.invoke()
                        longPressTriggered = true
                    }

                    try {
                        val success = tryAwaitRelease()
                        if (!longPressTriggered && success) {
                            onClick()
                        }
                    } finally {
                        job.cancel()
                        interactionSource.emit(PressInteraction.Release(press))
                        isPressed = false
                    }
                }
            )
        }
    } else Modifier

    // Анимации scale, alpha, overlayAlpha
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, tween(100))
    val alpha by animateFloatAsState(if (isPressed) 0.7f else 1f, tween(100))

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            this.alpha = alpha
        }
        .then(pressGestureModifier)
}




