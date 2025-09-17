
package test.webrtc.test.ui.navigation
/*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class SwipeAnchors {
    Swipe,
    End
}

private val slideInHorizontally: (fullWidth: Int) -> Int = { -it / 4 }

@Composable
fun SwipeBack(
    key: Any?,
    canSwipe: Boolean,
    onSwipeBack: () -> Unit,
    prev: @Composable () -> Unit,
    current: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val width = with(density) { maxWidth.toPx() }

        val spaceToSwipe = width * .1f*/
/*% of screen*//*


        val state = remember(key) {
            AnchoredDraggableState(
                initialValue = SwipeAnchors.Swipe,
                anchors = DraggableAnchors {
                    SwipeAnchors.Swipe at 0f
                    SwipeAnchors.End at width
                },
                positionalThreshold = { it },
                velocityThreshold = { constraints.maxWidth / 3f },
                animationSpec = SpringSpec(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        }

        println("currentValue: ${state.progress}, ${state.currentValue}, ${state.offset}")

        LaunchedEffect(state.currentValue) {
            if (state.currentValue == SwipeAnchors.End) onSwipeBack()
        }

        if (canSwipe) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = slideInHorizontally(size.width.toInt()).toFloat() -
                                slideInHorizontally(state.offset.absoluteValue.toInt())
                    }.drawWithContent {
                        drawContent()
                        drawRect(Color.Black, alpha = (1f - state.progress) * Color.Black.alpha)
                    }
            ) {
                if (state.offset > 0) {
                    prev()
                    if (state.currentValue == SwipeAnchors.Swipe) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {}
                        )
                    }
                }
            }
        }

        val shift = remember(this, width, spaceToSwipe) {
            (-width + spaceToSwipe.coerceIn(0f, width)).roundToInt()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.offset { IntOffset(state.requireOffset().coerceIn(offsetRange).roundToInt(), 0) }
                .offset { IntOffset(x = shift, 0) }
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal,
                    enabled = canSwipe && state.currentValue == SwipeAnchors.Swipe,
                    reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl
                )
                .offset { IntOffset(x = -shift, 0) }
                .graphicsLayer {
                    translationX = state.offset
                }
                .drawWithContent {
                    drawContent()
                    if (canSwipe) {
                        drawRect(
                            color = Color.Red,
                            alpha = .5f,
                            size = Size(
                                width = spaceToSwipe,
                                height = constraints.maxHeight.toFloat()
                            )
                        )
                    }
                }
        ) {
            current()
        }
    }
}*/
