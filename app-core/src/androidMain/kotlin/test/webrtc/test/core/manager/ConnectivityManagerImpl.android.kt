package test.webrtc.test.core.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.widget.Toast
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.mp.KoinPlatform

internal actual fun connectionFlow() : Flow<Boolean> {
    val context = KoinPlatform.getKoin().get<Context>()
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    return callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Toast.makeText(context, "onAvailable", Toast.LENGTH_LONG).show()
                trySend(true)
            }
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Toast.makeText(context, "onCapabilitiesChanged", Toast.LENGTH_LONG).show()
            }
            override fun onLost(network: Network) {
                Toast.makeText(context, "onLost", Toast.LENGTH_LONG).show()
                trySend(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}