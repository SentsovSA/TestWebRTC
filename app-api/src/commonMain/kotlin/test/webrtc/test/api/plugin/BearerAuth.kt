package test.webrtc.test.api.plugin

import RefreshTokenResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

internal class BearerAuthConfig {

    var loadTokens: suspend () -> Pair<String?, String?> = { null to null }
    var saveNewTokens: suspend (String, String) -> Unit = { _, _ -> }
    var refreshTokenError: suspend (RefreshTokenResponse.Error) -> Unit = {}

    fun loadTokens(block: suspend () -> Pair<String?, String?>) {
        loadTokens = block
    }

    fun onSaveNewTokens(block: suspend (String, String) -> Unit) {
        saveNewTokens = block
    }

    fun onRefreshTokenError(block: suspend (RefreshTokenResponse.Error) -> Unit) {
        refreshTokenError = block
    }

}

internal val BearerAuth = createClientPlugin("BearerAuth", ::BearerAuthConfig) {

    val loadTokens = pluginConfig.loadTokens
    val saveNewTokens = pluginConfig.saveNewTokens
    val refreshTokenError = pluginConfig.refreshTokenError

    onRequest { request, _ ->
        loadTokens().first?.let {
            request.headers[HttpHeaders.Authorization] = "Bearer $it"
        }
    }

    on(Send) { originalRequest ->
        val originalCall = proceed(originalRequest)

        if (originalCall.response.status != HttpStatusCode.Unauthorized)
            return@on originalCall

        val refreshToken = loadTokens().second ?: throw RefreshTokenNotFoundException()

        val refreshTokenResponse = client.post("v1/refreshToken") {
            expectSuccess = false

        }

        val body = refreshTokenResponse.body<RefreshTokenResponse>()
        if (refreshTokenResponse.status.isSuccess()) {
            saveNewTokens(body.accessToken!!, body.refreshToken!!)
            originalRequest.headers[HttpHeaders.Authorization] = "Bearer ${body.accessToken}"
        } else {
            refreshTokenError(body.error!!)
        }

        proceed(originalRequest)
    }

}

class RefreshTokenNotFoundException : Exception()