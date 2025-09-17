package test.webrtc.test.camera

import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.darwin.NSObject

internal class OrientationListener(
    private val onChanged: (UIDeviceOrientation) -> Unit
) : NSObject() {

    private val notificationName = platform.UIKit.UIDeviceOrientationDidChangeNotification

    @ObjCAction
    fun orientationDidChange(@Suppress("UNUSED_PARAMETER") arg: NSNotification) {
        onChanged(UIDevice.currentDevice.orientation)
    }

    fun register() {
        NSNotificationCenter.defaultCenter.addObserver(
            observer = this,
            selector = NSSelectorFromString(
                OrientationListener::orientationDidChange.name + ":"
            ),
            name = notificationName,
            `object` = null
        )
    }

    fun unregister() {
        NSNotificationCenter.defaultCenter.removeObserver(
            observer = this,
            name = notificationName,
            `object` = null
        )
    }
}