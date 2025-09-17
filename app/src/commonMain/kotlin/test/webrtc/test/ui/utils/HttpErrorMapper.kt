package test.webrtc.test.ui.utils

import test.webrtc.test.domain.entity.HttpRequestEntity
import org.jetbrains.compose.resources.StringResource
import test.webrtc.test.resources.Res
import test.webrtc.test.resources.error_connect_timeout
import test.webrtc.test.resources.error_network
import test.webrtc.test.resources.error_no_transformation_found
import test.webrtc.test.resources.error_request_timeout
import test.webrtc.test.resources.error_serialization
import test.webrtc.test.resources.error_socket_timeout

val HttpRequestEntity.toStringResource: StringResource? get() =
    when (this) {
        is HttpRequestEntity.Error.ConnectTimeout -> Res.string.error_connect_timeout
        is HttpRequestEntity.Error.RequestTimeout -> Res.string.error_request_timeout
        is HttpRequestEntity.Error.ContentConvert -> Res.string.error_serialization
        is HttpRequestEntity.Error.NoTransformationFound -> Res.string.error_no_transformation_found
        is HttpRequestEntity.Error.SocketTimeout -> Res.string.error_socket_timeout
        is HttpRequestEntity.Error.Network -> Res.string.error_network
        else -> null// TODO(gcd): other http errors
    }