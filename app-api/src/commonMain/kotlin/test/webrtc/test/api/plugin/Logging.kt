package test.webrtc.test.api.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

internal fun HttpClientConfig<*>.loggingPlugin(
    onLog: (String) -> Unit
) {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) = onLog(message)
        }
        level = LogLevel.ALL
    }
}