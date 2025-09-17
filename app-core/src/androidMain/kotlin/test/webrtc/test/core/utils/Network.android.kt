package test.webrtc.test.core.utils

import java.net.NetworkInterface

internal actual fun getIpAddressInLocalNetwork(): String? {
    val networkInterfaces = NetworkInterface.getNetworkInterfaces().iterator().asSequence()
    val localAddresses = networkInterfaces.flatMap {
        it.inetAddresses.asSequence()
            .filter { inetAddress ->
                inetAddress.isSiteLocalAddress &&
                inetAddress.hostAddress?.contains(":") == false &&
                inetAddress.hostAddress != "127.0.0.1"
            }
            .map { inetAddress -> inetAddress.hostAddress }
    }
    return localAddresses.firstOrNull()
}