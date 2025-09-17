package test.webrtc.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import test.webrtc.test.ui.RootContent
import test.webrtc.test.ui.utils.PlatformDeeplinkHolder

class MainActivity : ComponentActivity() {

    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val action = intent.action
        val data = intent.data

        if (Intent.ACTION_VIEW == action && data != null) {
            val scheme = data.scheme
            val host = data.host
            val path = data.path
        }

        PlatformDeeplinkHolder.deeplinkUrl = intent?.dataString

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        app = applicationContext as App

        setContent {
            /*val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    window.statusBarColor = Color.Transparent.toArgb()
                    window.navigationBarColor = Color.Transparent.toArgb()
                    val insets = WindowCompat.getInsetsController(window, view)
                    insets.isAppearanceLightStatusBars = true
                    insets.isAppearanceLightNavigationBars = true
                }
            }*/
            //androidx.lifecycle.compose.LocalLifecycleOwner.current
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                RootContent(app.viewModelsModule)
            }
        }
    }

}