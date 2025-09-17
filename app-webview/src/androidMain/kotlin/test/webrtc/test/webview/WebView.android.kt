package test.webrtc.test.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun WebView(
    modifier: Modifier,
    loadUrl: String,
    onPageStarted: (url: String) -> Unit
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    val keyboardController = LocalSoftwareKeyboardController.current
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webChromeClient = object : WebChromeClient() {
                }
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                        println("onPageStarted: $url")
                        onPageStarted(url)
                    }
                    override fun onPageFinished(view: WebView, url: String) {
                        println("onPageFinished: $url")
                    }
                }
                val defaultUserAgent = WebSettings.getDefaultUserAgent(context)
                settings.apply {
                    @SuppressLint("SetJavaScriptEnabled")
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    javaScriptCanOpenWindowsAutomatically = true
                    allowFileAccess = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    userAgentString = defaultUserAgent
                }
                loadUrl(loadUrl)
            }
        },
        update = { webView ->
            webView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    keyboardController?.show()
                    offsetY = 200f
                }
            }
        },
    )
}