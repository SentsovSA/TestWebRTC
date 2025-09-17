package test.webrtc.test.api.plugin

import test.webrtc.test.api.webSocketSerialization
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter

internal fun HttpClientConfig<*>.webSocketPlugin() {
    WebSockets {
        contentConverter = KotlinxWebsocketSerializationConverter(webSocketSerialization)
        pingInterval = 15_000
        maxFrameSize = Long.MAX_VALUE
    }
}