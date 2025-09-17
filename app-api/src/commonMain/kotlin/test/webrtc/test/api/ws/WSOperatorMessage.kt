package test.webrtc.test.api.ws

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("operator-message")
data class WSOperatorMessage(
    val message: String,
    val createdAt: Long? = null
) : WebSocketPacket()