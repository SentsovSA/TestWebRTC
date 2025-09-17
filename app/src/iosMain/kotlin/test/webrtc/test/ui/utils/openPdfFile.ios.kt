package test.webrtc.test.ui.utils

import platform.Foundation.*
import platform.UIKit.*
import platform.darwin.NSObject

private class PdfDelegate(
    private val viewController: UIViewController
) : NSObject(), UIDocumentInteractionControllerDelegateProtocol {

    override fun documentInteractionControllerViewControllerForPreview(controller: UIDocumentInteractionController): UIViewController {
        return viewController
    }
}

actual fun openPdfFile(path: String) {
    val url = NSURL.fileURLWithPath(path)
    val controller = UIDocumentInteractionController.interactionControllerWithURL(url)

    val keyWindow = UIApplication.sharedApplication.keyWindow
    val rootVC = keyWindow?.rootViewController
    val presentedVC = rootVC?.presentedViewController ?: rootVC

    if (presentedVC != null) {
        controller.delegate = PdfDelegate(presentedVC)
        controller.presentPreviewAnimated(true)
    } else {
        println("❌ Не удалось найти view controller для предпросмотра.")
    }
}