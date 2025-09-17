package test.webrtc.test.api

import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.encodeToString as efs
import kotlinx.serialization.decodeFromString as dfs

internal val restSerialization = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    //allowStructuredMapKeys = true
    isLenient = true
    coerceInputValues = true
    serializersModule = SerializersModule {

    }
}

internal val webSocketSerialization = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    //classDiscriminator = "event"
    /*serializersModule = SerializersModule {
        polymorphic(WebSocketPacket::class) {
            subclass(UserMessage::class)
        }
    }*/
}
internal inline fun <reified T> T.encodeToString() = Frame.Text(webSocketSerialization.efs<T>(this))
internal inline fun <reified T> Frame.Text.decodeFromString() = webSocketSerialization.dfs<T>(readText())