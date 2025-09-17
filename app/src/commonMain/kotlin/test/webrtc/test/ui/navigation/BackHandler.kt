package test.webrtc.test.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)

@Composable
fun SwipeBackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    val nav = LocalRouterProvider.current
    DisposableEffect(enabled) {
        nav.swipeBackCallback = if (enabled) onBack else null
        onDispose {
            nav.swipeBackCallback = null
        }
    }
}