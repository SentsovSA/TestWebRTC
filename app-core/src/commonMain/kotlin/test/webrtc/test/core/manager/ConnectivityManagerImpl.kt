package test.webrtc.test.core.manager

import test.webrtc.test.domain.manager.ConnectivityManager
import kotlinx.coroutines.flow.Flow

internal expect fun connectionFlow() : Flow<Boolean>

internal class ConnectivityManagerImpl private constructor(

): ConnectivityManager {

    companion object {
        fun create(
        ) : ConnectivityManager = ConnectivityManagerImpl(
        )
    }

    override fun observeConnection(): Flow<Boolean> {
        return connectionFlow()
    }

}