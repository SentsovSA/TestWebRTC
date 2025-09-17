package test.webrtc.test

import android.app.Application
import test.webrtc.test.domain.AndroidAppConfig
import test.webrtc.test.domain.ViewModelsModule
import test.webrtc.test.viewmodel.createViewModelsModule

class App : Application() {

    lateinit var viewModelsModule: ViewModelsModule

    override fun onCreate() {
        super.onCreate()
        viewModelsModule = createViewModelsModule {
            object : AndroidAppConfig {
                override val context = applicationContext
            }
        }
    }

}
