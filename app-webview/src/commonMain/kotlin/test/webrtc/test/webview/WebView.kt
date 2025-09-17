package test.webrtc.test.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    modifier: Modifier = Modifier,
    loadUrl: String,
    onPageStarted: (url: String) -> Unit = {}
)