package test.webrtc.test.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import platform.CoreGraphics.CGRectZero
import kotlinx.cinterop.readValue
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIColor
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@Composable
actual fun WebView(
    modifier: Modifier,
    loadUrl: String,
    onPageStarted: (url: String) -> Unit
) {
    UIKitView(
        modifier = modifier,
        factory = {
            WKWebView(
                frame = CGRectZero.readValue(),
                configuration = WKWebViewConfiguration().apply {
                    allowsInlineMediaPlayback = true
                    defaultWebpagePreferences.allowsContentJavaScript = true
                    preferences.apply {
                        javaScriptEnabled = true
                    }
                    userContentController.addUserScript(
                        WKUserScript(
                            source = """
                try {
    if (window.location.hostname.indexOf('ost.group') >= 0) {
        const url = new URL(window.location.href);
        const cleanUrl = url.origin + url.pathname;
        window.webkit.messageHandlers.closeWebView.postMessage(cleanUrl);
    }
} catch (e) { /* ничего не делаем */ }
                """.trimIndent(),
                            injectionTime = WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentStart,
                            forMainFrameOnly = false
                        )
                    )
                    userContentController.addScriptMessageHandler(
                        object : NSObject(), WKScriptMessageHandlerProtocol {
                            override fun userContentController(
                                userContentController: WKUserContentController,
                                didReceiveScriptMessage: WKScriptMessage
                            ) {
                                val url = didReceiveScriptMessage.body as? String ?: "Unknown URL"
                                onPageStarted(url)
                            }
                        },
                        "closeWebView"
                    )
                }
            ).apply {
                navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                    /*override fun webView(
                        webView: WKWebView,
                        decidePolicyForNavigationAction: WKNavigationAction,
                        decisionHandler: (WKNavigationActionPolicy) -> Unit
                    ) {
                        val url = decidePolicyForNavigationAction.request.URL?.absoluteString ?: ""
                        if (url.contains("main-service-ci.test.ost.group/")) {
                            onPageStarted(url)
                            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyCancel)
                        } else {
                            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
                        }
                    }*/
                }
                allowsBackForwardNavigationGestures = true
                setBackgroundColor(UIColor.whiteColor)
                with(scrollView) {
                    bounces = true
                    scrollEnabled = true
                    showsHorizontalScrollIndicator = true
                    showsVerticalScrollIndicator = true
                    setBackgroundColor(UIColor.whiteColor)
                    pinchGestureRecognizer?.enabled = true
                }
                setOpaque(false)
                loadRequest(NSURLRequest(NSURL(string = loadUrl)))
            }
        },
        onRelease = {
            it.navigationDelegate = null
        }
    )
}