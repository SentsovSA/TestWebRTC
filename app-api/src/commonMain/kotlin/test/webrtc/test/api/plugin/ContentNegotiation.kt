package test.webrtc.test.api.plugin

import test.webrtc.test.api.restSerialization
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

internal fun HttpClientConfig<*>.serializationPlugin() {
    install(ContentNegotiation) {
        json(restSerialization)
    }
}
