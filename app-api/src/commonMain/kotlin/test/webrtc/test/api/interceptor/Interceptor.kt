package test.webrtc.test.api.interceptor

import io.ktor.client.plugins.HttpSend

internal fun HttpSend.interceptor() {
    intercept { request ->
        execute(request)
    }
}