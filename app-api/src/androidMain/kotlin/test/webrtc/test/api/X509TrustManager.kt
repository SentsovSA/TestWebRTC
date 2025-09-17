package test.webrtc.test.api

import java.security.cert.X509Certificate

@Suppress("CustomX509TrustManager")
internal object X509TrustManager : javax.net.ssl.X509TrustManager {

    override fun getAcceptedIssuers(): Array<X509Certificate?> = arrayOf()

    @Suppress("TrustAllX509TrustManager")
    override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}

    @Suppress("TrustAllX509TrustManager")
    override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}


}