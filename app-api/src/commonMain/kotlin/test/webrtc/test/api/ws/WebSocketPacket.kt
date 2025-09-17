package test.webrtc.test.api.ws

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("event")
sealed class WebSocketPacket