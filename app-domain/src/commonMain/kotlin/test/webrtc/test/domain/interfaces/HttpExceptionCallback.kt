package test.webrtc.test.domain.interfaces

interface HttpExceptionCallback {
    suspend fun onRequestTimeoutException(message: String?)
    suspend fun onConnectTimeoutException(message: String?)
    suspend fun onSocketTimeoutException(message: String?)
    suspend fun onContentConvertException(message: String?)
    suspend fun onNoTransformationFoundException(message: String?)
    suspend fun onNetworkException(message: String?)
}

