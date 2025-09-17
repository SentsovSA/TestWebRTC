package test.webrtc.test

import androidx.compose.ui.window.ComposeUIViewController
import test.webrtc.test.domain.IOSAppConfig
import test.webrtc.test.ui.RootContent
import test.webrtc.test.viewmodel.createViewModelsModule
import platform.UIKit.UIViewController

@Suppress("unused")
fun mainViewController() : UIViewController {
    val viewModelsModule = createViewModelsModule {
        IOSAppConfig
    }
    return ComposeUIViewController {
        RootContent(viewModelsModule)
    }
}
