package test.webrtc.test.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

internal actual fun httpClient(
    baseUrl: String,
    onLog: (String) -> Unit,
    currentAccessToken: suspend () -> String?,
    currentRefreshToken: suspend () -> String?,
    onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
    onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit,
    httpClientConfig: HttpClientConfig<*>.() -> Unit
) = HttpClient(OkHttp) {
    engine {
        config {
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, arrayOf<TrustManager>(X509TrustManager), null)
            }
            sslSocketFactory(sslContext.socketFactory, X509TrustManager)
        }
    }
    httpClientConfig()
}