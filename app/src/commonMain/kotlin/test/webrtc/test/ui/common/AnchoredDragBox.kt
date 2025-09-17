
package test.webrtc.test.ui.common
/*
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

enum class DragAnchors {
    Start,
    Center,
    End,
}

enum class SwipeDirection {
    EndToStart,
    StartToEnd,
    Both
}

@Composable
fun AnchoredDragBox(
    modifier: Modifier = Modifier,
    state: AnchoredDraggableState<DragAnchors> = rememberAnchoredDraggableState(),
    swipeDirection: SwipeDirection = SwipeDirection.EndToStart,
    startContentWidth: Dp = 0.dp,
    startContent: @Composable (RowScope.(anchoredDraggableState: AnchoredDraggableState<DragAnchors>, startSwipeProgress: Float) -> Unit)? = null,
    endContentWidth: Dp = 0.dp,
    endContent: @Composable (RowScope.(anchoredDraggableState: AnchoredDraggableState<DragAnchors>, endSwipeProgress: Float) -> Unit)? = null,
    content: @Composable BoxScope.(anchoredDraggableState: AnchoredDraggableState<DragAnchors>, startSwipeProgress: Float, endSwipeProgress: Float) -> Unit,
) {
    val startWidthPx = with(LocalDensity.current) { startContentWidth.toPx() }
    val endWidthPx = with(LocalDensity.current) { endContentWidth.toPx() }

    val draggableAnchors : DraggableAnchors<DragAnchors> = when (swipeDirection) {
        SwipeDirection.StartToEnd -> DraggableAnchors {
            DragAnchors.Start at startWidthPx
            DragAnchors.Center at 0f
        }

        SwipeDirection.EndToStart -> DraggableAnchors {
            DragAnchors.Center at 0f
            DragAnchors.End at -endWidthPx
        }

        SwipeDirection.Both -> DraggableAnchors {
            DragAnchors.Start at -startWidthPx
            DragAnchors.Center at 0f
            DragAnchors.End at endWidthPx
        }
    }

    state.updateAnchors(draggableAnchors)

    val offsetRange = when (swipeDirection) {
        SwipeDirection.StartToEnd -> 0f..Float.POSITIVE_INFINITY
        SwipeDirection.EndToStart -> Float.NEGATIVE_INFINITY..0f
        SwipeDirection.Both -> Float.NEGATIVE_INFINITY..Float.POSITIVE_INFINITY
    }
    val startSwipeProgress by animateFloatAsState(
        targetValue = if (state.requireOffset() > 0f) {
            (state.requireOffset() / startWidthPx).absoluteValue
        } else 0f, label = "startSwipeProgress"
    )
    val endSwipeProgress by animateFloatAsState(
        targetValue = if (state.requireOffset() < 0f) {
            (state.requireOffset() / endWidthPx).absoluteValue
        } else 0f, label = "endSwipeProgress"
    )
    val startContentLiveWidth = startContentWidth * startSwipeProgress
    val endContentLiveWidth = endContentWidth * endSwipeProgress
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clipToBounds()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = when (swipeDirection) {
                SwipeDirection.StartToEnd -> Arrangement.Start
                SwipeDirection.EndToStart -> Arrangement.End
                SwipeDirection.Both -> Arrangement.SpaceBetween
            }
        ) {
            if (swipeDirection in listOf(
                    SwipeDirection.StartToEnd,
                    SwipeDirection.Both
                ) && startContent != null
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(startContentLiveWidth)
                        .clipToBounds()
                ) {
                    startContent(state, startSwipeProgress)
                }
            }
            if (swipeDirection in listOf(
                    SwipeDirection.EndToStart,
                    SwipeDirection.Both
                ) && endContent != null
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(endContentLiveWidth)
                        .clipToBounds()
                ) {
                    endContent(state, endSwipeProgress)
                }
            }
        } // Bottom Layer
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .offset {
                IntOffset(state.requireOffset().coerceIn(offsetRange).roundToInt(), 0)
            }
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal
            )
        ) {
            content(state, startSwipeProgress, endSwipeProgress)
        }
    }
}

@Composable
fun rememberAnchoredDraggableState(
    initialValue: DragAnchors = DragAnchors.Center,
    positionalThreshold: (distance: Float) -> Float = { distance -> distance * 0.5f },
    velocityThreshold: Dp = 100.dp,
    animationSpec: SpringSpec<Float> = SpringSpec(),
): AnchoredDraggableState<DragAnchors> {
    val density = LocalDensity.current
    return remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            positionalThreshold = positionalThreshold,
            velocityThreshold = { with(density) { velocityThreshold.toPx() } },
            animationSpec = animationSpec,
        )
    }
}

@Composable
fun rememberAnchoredDraggableState(
    key: Any?,
    initialValue: DragAnchors = DragAnchors.Center,
    positionalThreshold: (distance: Float) -> Float = { distance -> distance * 0.5f },
    velocityThreshold: Dp = 100.dp,
    animationSpec: SpringSpec<Float> = SpringSpec(),
): AnchoredDraggableState<DragAnchors> {
    val density = LocalDensity.current
    return remember(key) {
        AnchoredDraggableState(
            initialValue = initialValue,
            positionalThreshold = positionalThreshold,
            velocityThreshold = { with(density) { velocityThreshold.toPx() } },
            animationSpec = animationSpec
        )
    }
}

*/
/*
@Composable
private fun AnchoredDragBoxExample() {
    val coroutineScope = rememberCoroutineScope()
    AnchoredDragBox(
        modifier = Modifier.fillMaxWidth(),
        swipeDirection = SwipeDirection.StartToEnd,
        startContentWidth = 60.dp,
        startContent = { anchoredDraggableState, endSwipeProgress ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Red)
                    .clickable {
                        coroutineScope.launch {
                            anchoredDraggableState.animateTo(DragAnchors.Center)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("cdfg vfg thb tny")
            }
        }
    ) { _, _, _ ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0xfffdba00)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Swipe Left", color = Color.Black, fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}*/

