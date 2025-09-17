package test.webrtc.test.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

internal expect fun httpClient(
    baseUrl: String,
    onLog: (String) -> Unit,
    currentAccessToken: suspend () -> String?,
    currentRefreshToken: suspend () -> String?,
    onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
    onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit,
    httpClientConfig: HttpClientConfig<*>.() -> Unit
): HttpClient