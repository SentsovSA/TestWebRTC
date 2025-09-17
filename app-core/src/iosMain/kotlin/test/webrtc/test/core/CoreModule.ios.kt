package test.webrtc.test.core

import test.webrtc.test.core.di.initKoin
import test.webrtc.test.domain.AppConfig
import test.webrtc.test.domain.AppConfigFactory
import test.webrtc.test.domain.CoreModule
import test.webrtc.test.domain.IOSAppConfig

actual fun <T : AppConfig> createCoreModule(configFactory: AppConfigFactory<T>) : CoreModule {
    val config = configFactory() as IOSAppConfig
    initKoin()
    return CoreModuleImpl()
}