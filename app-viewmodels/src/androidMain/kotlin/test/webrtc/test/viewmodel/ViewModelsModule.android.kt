package test.webrtc.test.viewmodel

import test.webrtc.test.core.createCoreModule
import test.webrtc.test.domain.AppConfig
import test.webrtc.test.domain.AppConfigFactory
import test.webrtc.test.domain.ViewModelsModule

actual fun <T : AppConfig> createViewModelsModule(
    configFactory: AppConfigFactory<T>
) : ViewModelsModule {
    val core = createCoreModule { configFactory() }
    return ViewModelsModuleImpl(core)
}