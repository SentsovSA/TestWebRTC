package test.webrtc.test.api.plugin

import RefreshTokenResponse
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.http.isSuccess

internal fun HttpClientConfig<*>.authPlugin(
    currentAccessToken: suspend () -> String?,
    currentRefreshToken: suspend () -> String?,
    onSaveNewTokens: suspend (accessToken: String, refreshToken: String) -> Unit,
    onRefreshTokenError: suspend (error: RefreshTokenResponse.Error) -> Unit
) {
    Auth {
        val provider = BearerAuthProvider(
            loadTokens = {
                val accessToken = currentAccessToken()
                val refreshToken = currentRefreshToken()
                if (accessToken != null && refreshToken != null)
                    BearerTokens(accessToken, refreshToken)
                else null
            },
            refreshTokens = a@ {
                //val reason = this.response.body<ApiResponse<Nothing>>().error()
                //println("GGGGGGGGGGGGGGGGG: $reason")
                val refreshToken = currentRefreshToken() ?: return@a null
                val response = client.post("v1/refreshToken") {
                    expectSuccess = false

                    markAsRefreshTokenRequest()
                }
                if (response.status.isSuccess()) {
                    val body = response.body<RefreshTokenResponse>()
                    onSaveNewTokens(body.accessToken!!, body.refreshToken!!)
                    BearerTokens(body.accessToken, body.refreshToken)
                } else {
                    val body = response.body<RefreshTokenResponse>()
                    onRefreshTokenError(body.error!!)
                    null
                }
            },
            sendWithoutRequestCallback = {
                //it.url.encodedPath == "v1/refreshToken"
                true
            },
            realm = null
        )
        providers.add(provider)
    }
}