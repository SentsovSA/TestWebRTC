package test.webrtc.test.api.utils

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.ContentConvertException

suspend inline fun <reified T : Any> Throwable.httpErrorHandler(
    crossinline onConnectTimeout: suspend (String?) -> Unit,
    crossinline onRequestTimeout: suspend (String?) -> Unit,
    crossinline onSocketTimeout: suspend (String?) -> Unit,
    crossinline onContentConvert: suspend (String?) -> Unit,
    crossinline onNoTransformationFound: suspend (String?) -> Unit,
    crossinline onResponse: suspend (statusCode: Int, T) -> Unit,
    crossinline onNetwork: suspend (String?) -> Unit
) {
    when (val it = this) {
        is ConnectTimeoutException -> onConnectTimeout(it.message)
        is HttpRequestTimeoutException -> onRequestTimeout(it.message)
        is SocketTimeoutException -> onSocketTimeout(it.message)
        is ContentConvertException -> onContentConvert(it.message)
        is ResponseException -> {
            try {
                onResponse(it.response.status.value, it.response.body())
            } catch (e: NoTransformationFoundException) {
                onNoTransformationFound(e.message)
            }
        }
        else -> onNetwork(it.message)
    }
}