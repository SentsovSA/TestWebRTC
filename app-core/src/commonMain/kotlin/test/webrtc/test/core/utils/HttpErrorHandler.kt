package test.webrtc.test.core.utils

import test.webrtc.test.api.utils.httpErrorHandler
import test.webrtc.test.core.interfaces.InternalHttpExceptionCallbacks
import io.github.aakira.napier.Napier

internal suspend inline fun <reified T : Any> Throwable.errorHandler(
    i: InternalHttpExceptionCallbacks,
    crossinline onResponse: suspend (statusCode: Int, response: T) -> Unit
) {
    Napier.e(this.message ?: "null", this)
    this.httpErrorHandler<T>(
        onResponse = onResponse,
        onRequestTimeout = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onRequestTimeoutException(message)
            }
        },
        onConnectTimeout = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onConnectTimeoutException(message)
            }
        },
        onSocketTimeout = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onSocketTimeoutException(message)
            }
        },
        onContentConvert = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onContentConvertException(message)
            }
        },
        onNoTransformationFound = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onNoTransformationFoundException(message)
            }
        },
        onNetwork = { message ->
            i.notifyHttpExceptionCallbacks {
                it.onNetworkException(message)
            }
        }
    )
}