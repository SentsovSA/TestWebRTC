package test.webrtc.test.core.di.module

import test.webrtc.test.api.Api
import test.webrtc.test.core.BASE_URL
import test.webrtc.test.data.repository.SettingsRepository
import io.github.aakira.napier.Napier
import org.koin.dsl.module

internal val networkModule = module {
    single<Api> {
        val settings = get<SettingsRepository>()
        var baseUrl = BASE_URL
        /*if (DEV_MODE) {
            getIpAddressInLocalNetwork()?.let {
                baseUrl = "http://$it:1234"
            }
        }
        Napier.i { "BASE_URL: $baseUrl" }*/
        Api.create(
            baseUrl = baseUrl,
            onLog = Napier::d,
            currentAccessToken = {
                settings.getAccessToken()
            },
            currentRefreshToken = {
                settings.getRefreshToken()
            },
            onSaveNewTokens = { accessToken, refreshToken ->
                settings.setAccessToken(accessToken)
                settings.setRefreshToken(refreshToken)
            },
            onRefreshTokenError = { error ->
                settings.removeAccessToken()
                settings.removeRefreshToken()
            }
        )
    }
}