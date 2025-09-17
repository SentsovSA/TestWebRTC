package test.webrtc.test.api.ws

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user-message")
data class WSUserMessage(
    val message: String,
    val createdAt: Long? = null
) : WebSocketPacket()