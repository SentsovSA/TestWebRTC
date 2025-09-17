package test.webrtc.test.api

import test.webrtc.test.api.interceptor.interceptor
import test.webrtc.test.api.plugin.BearerAuth
import test.webrtc.test.api.plugin.defaultRequestPlugin
import test.webrtc.test.api.plugin.httpTimeoutPlugin
import test.webrtc.test.api.plugin.loggingPlugin
import test.webrtc.test.api.plugin.serializationPlugin
import test.webrtc.test.api.plugin.webSocketPlugin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin

abstract class Api {

    companion object {
        fun create(
            baseUrl: String,
            onLog: (String) -> Unit,
            currentAccessToken: suspend () -> String?,
            currentRefreshToken: suspend () -> String?,
            onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
            onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit
        ) : Api = ApiImpl(
            baseUrl = baseUrl,
            onLog = onLog,
            currentAccessToken = currentAccessToken,
            currentRefreshToken = currentRefreshToken,
            onSaveNewTokens = onSaveNewTokens,
            onRefreshTokenError = onRefreshTokenError
        )
    }

    internal abstract val client: HttpClient

}

private class ApiImpl(
    baseUrl: String,
    onLog: (String) -> Unit,
    currentAccessToken: suspend () -> String?,
    currentRefreshToken: suspend () -> String?,
    onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
    onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit
) : Api() {

    override val client = httpClient(
        baseUrl = baseUrl,
        onLog = onLog,
        currentAccessToken = currentAccessToken,
        currentRefreshToken = currentRefreshToken,
        onSaveNewTokens = onSaveNewTokens,
        onRefreshTokenError = onRefreshTokenError
    ) {
        expectSuccess = true
        followRedirects = true
        developmentMode = true
        defaultRequestPlugin(baseUrl)
        install(BearerAuth) {
            loadTokens {
                currentAccessToken() to currentRefreshToken()
            }
            onSaveNewTokens(onSaveNewTokens)
            onRefreshTokenError(onRefreshTokenError)
        }
        serializationPlugin()
        httpTimeoutPlugin()
        loggingPlugin(onLog)
        webSocketPlugin()
    }

    init {
        client.plugin(HttpSend).interceptor()
    }

}