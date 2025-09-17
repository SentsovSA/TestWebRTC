package test.webrtc.test.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

internal actual fun httpClient(
    baseUrl: String,
    onLog: (String) -> Unit,
    currentAccessToken: suspend () -> String?,
    currentRefreshToken: suspend () -> String?,
    onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
    onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit,
    httpClientConfig: HttpClientConfig<*>.() -> Unit
) = HttpClient(Darwin) {
    engine {
        /*maxConnectionsCount = MAX_CONNECTION
        //requestTimeout = 60_000
        endpoint {
            maxConnectionsPerRoute = MAX_CONNECTION
            pipelineMaxSize = 200
            keepAliveTime = 5_000
            connectTimeout = 300_000
            connectAttempts = 100
        }*/
    }
    httpClientConfig()
}