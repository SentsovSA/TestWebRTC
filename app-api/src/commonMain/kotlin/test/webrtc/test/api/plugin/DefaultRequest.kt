package test.webrtc.test.api.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal fun HttpClientConfig<*>.defaultRequestPlugin(baseUrl: String) {
    defaultRequest {
        url("$baseUrl/")
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}