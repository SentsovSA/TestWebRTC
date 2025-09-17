package test.webrtc.test.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import test.webrtc.test.ui.navigation.LocalRouterProvider
import test.webrtc.test.ui.navigation.Route
import test.webrtc.test.ui.screen.SplashScreen
import test.webrtc.test.ui.screen.UnimplementedScreen

@Composable
private fun Animation(
    route: Route,
    content: @Composable (Route) -> Unit
) {
    val animationSpec = remember {
        spring(
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = IntOffset.VisibilityThreshold
        )
    }
    AnimatedContent(
        targetState = route,
        transitionSpec = {
            val (initial, target) = if (false/*isPop*/)
                ({ size: Int -> -size }) to ({ size: Int -> size })
            else
                ({ size: Int -> size }) to ({ size: Int -> -size })
            slideInHorizontally(animationSpec, initial) togetherWith
                    slideOutHorizontally(animationSpec, target)
        }
    ) {
        content(it)
    }
}

/*@Composable
fun SwipeBack(
    key: Any,
    canSwipe: Boolean,
    onSwipeBack: () -> Unit,
    prev: @Composable () -> Unit,
    current: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val dragWidthPx = with(density) { 300.dp.toPx() }

    val state = remember {
        AnchoredDraggableState<Float>(
            initialValue = 0f,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            animationSpec = spring(stiffness = Spring.StiffnessMedium)
        )
    }

    val anchors = DraggableAnchors {
        0f at 0f
        1f at dragWidthPx
    }

    LaunchedEffect(state.currentValue) {
        if (state.currentValue == 1f) {
            onSwipeBack()
            scope.launch {
                state.updateAnchors(anchors)
                state.snapTo(0f)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal,
                enabled = canSwipe
            )
    ) {
        if (canSwipe) {
            Box(modifier = Modifier.fillMaxSize()) {
                prev()
            }
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        state
                            .requireOffset()
                            .coerceAtLeast(0f)
                            .roundToInt(), 0
                    )
                }
        ) {
            current()
        }
    }

    LaunchedEffect(key) {
        state.updateAnchors(anchors)
        state.snapTo(0f)
    }
}*/



@Composable
fun Routes(route: Route) {
    val nav = LocalRouterProvider.current
    key(route) {
        when (route.path) {

            "/splash" -> {
                SplashScreen()
            }

            else -> UnimplementedScreen(
                text = "Not found: ${route.path}"
            )
        }
    }
}