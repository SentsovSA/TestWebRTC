package test.webrtc.test.viewmodel

import test.webrtc.test.domain.AppConfig
import test.webrtc.test.domain.AppConfigFactory
import test.webrtc.test.domain.CoreModule
import test.webrtc.test.domain.ViewModels
import test.webrtc.test.domain.ViewModelsModule

expect fun <T : AppConfig> createViewModelsModule(configFactory: AppConfigFactory<T>): ViewModelsModule

internal class ViewModelsModuleImpl(
    private val core: CoreModule
) : ViewModelsModule {

    init {
        core.viewModelsFactory(object : ViewModels.Factory {

        })
    }

}