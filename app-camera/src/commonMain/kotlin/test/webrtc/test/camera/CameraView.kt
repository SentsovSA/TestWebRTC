package test.webrtc.test.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal typealias CameraQRCodeCallback = (x: Double, y: Double, w: Double, h: Double, data: String) -> Unit

@Composable
expect fun CameraView(
    modifier: Modifier = Modifier,
    onFoundQRCode: (String) -> Unit = { }
)