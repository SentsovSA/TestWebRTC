package test.webrtc.test.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.valentinilk.shimmer.LocalShimmerTheme
import test.webrtc.test.domain.ViewModelsModule
import test.webrtc.test.ui.navigation.LocalRouterProvider
import test.webrtc.test.ui.navigation.Router
import test.webrtc.test.ui.theme.AppTheme
import test.webrtc.test.ui.theme.shimmerTheme
import test.webrtc.test.ui.utils.PlatformDeeplinkHolder

internal val LocalViewModelsModuleProvider = staticCompositionLocalOf<ViewModelsModule> {
    error("ViewModelsModule not provided")
}

@Composable
internal fun RootContent(viewModelsModule: ViewModelsModule) {
    CompositionLocalProvider(
        LocalViewModelsModuleProvider providesDefault viewModelsModule,
        LocalShimmerTheme provides shimmerTheme
    ) {

        var loggedIn by remember { mutableStateOf(false) }

        val deeplink = PlatformDeeplinkHolder.deeplinkUrl

        AppTheme(darkTheme = false) {

            val router = remember {
                Router(
                    initialPath = "/splash",
                    onPop = { route -> println("onPop: ${route.path}") }
                )
            }

            val currentRoute by router.currentRoute.collectAsState()

            key(currentRoute.path) {
                CompositionLocalProvider(
                    LocalRouterProvider providesDefault router
                ) {
                    Routes(currentRoute)
                }
            }
        }
    }
}
