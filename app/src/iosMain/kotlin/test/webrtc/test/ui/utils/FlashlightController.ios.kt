package test.webrtc.test.ui.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*

actual object FlashlightController {
    private val device: AVCaptureDevice? get() =
        AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)

    actual fun toggle() {
        device?.let {
            if (it.torchMode == AVCaptureTorchModeOn) {
                turnOff()
            } else {
                turnOn()
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun turnOn() {
        val dev = device ?: return
        if (dev.hasTorch() && dev.isTorchModeSupported(AVCaptureTorchModeOn)) {
            dev.lockForConfiguration(null)
            dev.setTorchModeOnWithLevel(1.0F, null)
            dev.unlockForConfiguration()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun turnOff() {
        val dev = device ?: return
        if (dev.hasTorch() && dev.isTorchModeSupported(AVCaptureTorchModeOff)) {
            dev.lockForConfiguration(null)
            dev.torchMode = AVCaptureTorchModeOff
            dev.unlockForConfiguration()
        }
    }

    actual fun isSupported(): Boolean {
        return device?.hasTorch() == true
    }
}
