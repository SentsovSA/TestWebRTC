package test.webrtc.test.core

import test.webrtc.test.domain.AppConfig
import test.webrtc.test.domain.AppConfigFactory
import test.webrtc.test.domain.CoreModule
import test.webrtc.test.domain.ViewModels
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

expect fun <T : AppConfig> createCoreModule(configFactory: AppConfigFactory<T>) : CoreModule

internal class CoreModuleImpl : CoreModule {

    init {
        Napier.base(DebugAntilog("[]"))
    }

    private val di get() = KoinPlatform.getKoin()

    override fun viewModelsFactory(factory: ViewModels.Factory) {
        di.loadModules(listOf(module {

        }))
    }
}

