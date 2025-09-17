package test.webrtc.test.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIColor
import platform.UIKit.UIView

@Composable
actual fun CameraView(
    modifier: Modifier,
    onFoundQRCode: (String) -> Unit
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(Size.Zero) }
    val camera = remember {
        Camera(
            onFoundQRCode = { x, y, w, h, data ->
                offset = Offset((x * 1000).toFloat(), (y * 1000).toFloat())
                size = Size((w * 1000).toFloat(), (h * 1000).toFloat())
                onFoundQRCode(data)
            }
        )
    }
    DisposableEffect(Unit) {
        val listener = OrientationListener { orientation ->
            camera.setOrientation(orientation)
        }
        listener.register()
        onDispose {
            listener.unregister()
        }
    }
    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawRoundRect(
                    Color.Red,
                    topLeft = offset,
                    size = size,
                    cornerRadius = CornerRadius(5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
    ) {
        UIKitView(
            modifier = Modifier
                .fillMaxSize(),
            background = Color.Black,
            factory = {
                UIView().apply {
                    println("Calling prepare")
                    backgroundColor = UIColor.blackColor
                    camera.prepare(layer)
                }
            },
            onResize = { view, rect ->
                CATransaction.begin()
                CATransaction.setValue(true, kCATransactionDisableActions)
                view.layer.setFrame(rect)
                camera.setFrame(rect)
                CATransaction.commit()
            }
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            camera.stopRunning()
        }
    }
}