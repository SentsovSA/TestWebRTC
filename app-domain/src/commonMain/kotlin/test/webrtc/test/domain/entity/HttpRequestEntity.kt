package test.webrtc.test.domain.entity

sealed class HttpRequestEntity {
    data object Idle : HttpRequestEntity()
    data object Loading : HttpRequestEntity()
    data object Refresh : HttpRequestEntity()
    data object Completed : HttpRequestEntity()
    sealed class Error : HttpRequestEntity() {
        data object ConnectTimeout : Error()
        data object RequestTimeout : Error()
        data object SocketTimeout : Error()
        data object ContentConvert : Error()
        data object NoTransformationFound : Error()
        data object Network : Error()
    }
}