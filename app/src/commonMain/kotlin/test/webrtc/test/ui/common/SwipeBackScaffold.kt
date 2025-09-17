
package test.webrtc.test.ui.common
/*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import group.ost.paymentservice.ui.navigation.LocalRouterProvider
import group.ost.paymentservice.ui.Routes
import group.ost.paymentservice.ui.navigation.BackHandler
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class SwipeAnchors {
    Idle,
    End
}

private val slideInHorizontally: (fullWidth: Int) -> Int = { -it / 4 }
private val shadowColor = Color.Black

@Composable
fun SwipeBackScaffold(
    modifier: Modifier = Modifier,
    canPop: Boolean,
    prevContent: @Composable () -> Unit,
    onPop: () -> Unit,
    contentWindowInsets: WindowInsets = WindowInsets(0.dp),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val width = with(density) { maxWidth.toPx() }

        val state = remember {
            AnchoredDraggableState(
                initialValue = SwipeAnchors.Idle,
                anchors = DraggableAnchors {
                    SwipeAnchors.Idle at 0f
                    SwipeAnchors.End at width
                },
                positionalThreshold = { it * .9f },
                velocityThreshold = { constraints.maxWidth / 3f },
                animationSpec = SpringSpec(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        }

        println("progress: ${state.isAnimationRunning}, ${state.progress}")

        //val startContentLiveWidth = startWidthPx * progress

        if (state.progress != 1f && state.progress > .0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = slideInHorizontally(size.width.toInt()).toFloat() -
                                slideInHorizontally(state.offset.absoluteValue.toInt())
                    }.drawWithContent {
                        drawContent()
                        drawRect(shadowColor, alpha = (1f - state.progress) * shadowColor.alpha)
                    }
            ) {
                prevContent()
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {}
            )
        }

        when (state.currentValue) {
            SwipeAnchors.Idle -> {
                val spaceToSwipe = width * .05f*/
/*% of screen*//*

                val shift = with(density) {
                    remember(this, width, spaceToSwipe) {
                        (-width + spaceToSwipe.coerceIn(0f, width)).roundToInt()
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        //.offset { IntOffset(state.requireOffset().coerceIn(offsetRange).roundToInt(), 0) }
                        .offset { IntOffset(x = shift, 0) }
                        .anchoredDraggable(
                            state = state,
                            orientation = Orientation.Horizontal,
                            enabled = canPop && state.currentValue == SwipeAnchors.Idle,
                            //reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl
                        )
                        .offset { IntOffset(x = -shift, 0) }
                        .graphicsLayer {
                            translationX = state.offset
                        }
                    */
/*.drawWithContent {
                        drawContent()
                        drawRect(
                            color = Color.Red,
                            alpha = .5f,
                            size = Size(
                                width = constraints.maxWidth.toFloat() * .1f,
                                height = constraints.maxHeight.toFloat()
                            )
                        )
                    }*//*

                ) {
                    Scaffold(
                        modifier = modifier,
                        contentWindowInsets = contentWindowInsets,
                        scaffoldState = scaffoldState,
                        topBar = topBar,
                        bottomBar = bottomBar,
                        snackbarHost = snackbarHost,
                        floatingActionButton = floatingActionButton,
                        floatingActionButtonPosition = floatingActionButtonPosition,
                        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
                        drawerContent = drawerContent,
                        drawerGesturesEnabled = drawerGesturesEnabled,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        drawerScrimColor = drawerScrimColor,
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        content = content
                    )
                }
            }
            SwipeAnchors.End -> {
                LaunchedEffect(Unit) {
                    onPop()
                }
            }
        }
    }
}*/
