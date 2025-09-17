package test.webrtc.test.domain.manager

import kotlinx.coroutines.flow.Flow

interface ConnectivityManager {
    fun observeConnection() : Flow<Boolean>
}