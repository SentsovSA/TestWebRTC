package test.webrtc.test.core

import test.webrtc.test.core.di.initKoin
import test.webrtc.test.domain.AndroidAppConfig
import test.webrtc.test.domain.AppConfig
import test.webrtc.test.domain.AppConfigFactory
import test.webrtc.test.domain.CoreModule
import org.koin.android.ext.koin.androidContext

actual fun <T : AppConfig> createCoreModule(
    configFactory: AppConfigFactory<T>
) : CoreModule {
    val config = configFactory() as AndroidAppConfig
    initKoin {
        androidContext(config.context)
    }
    return CoreModuleImpl()
}
