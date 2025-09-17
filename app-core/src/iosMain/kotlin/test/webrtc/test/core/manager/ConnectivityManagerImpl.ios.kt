package test.webrtc.test.core.manager

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal actual fun connectionFlow() : Flow<Boolean> {
    return callbackFlow {
        awaitClose {
        }
    }
}