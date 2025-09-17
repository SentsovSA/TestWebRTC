package test.webrtc.test.ui.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import test.webrtc.test.camera.CameraView

@Composable
fun CameraDialog(
    show: Boolean,
    onFoundQRCode: (String) -> Unit = {},
    onDismissRequest: () -> Unit
) {
    if (!show) return
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        CameraView(
            modifier = Modifier
                .fillMaxSize(),
            onFoundQRCode = onFoundQRCode
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .navigationBarsPadding()
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = onDismissRequest
            ) {
                Icon(
                    Icons.Default.Close,
                    tint = Color.Red,
                    contentDescription = null
                )
            }
        }
    }
}