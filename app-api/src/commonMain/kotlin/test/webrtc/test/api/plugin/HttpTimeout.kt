package test.webrtc.test.api.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout

internal fun HttpClientConfig<*>.httpTimeoutPlugin() {
    install(HttpTimeout) {
        connectTimeoutMillis = 30_000
        requestTimeoutMillis = 60_000
        socketTimeoutMillis = 600_000
    }
}