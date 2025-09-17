package test.webrtc.test.camera

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.useContents
import kotlinx.cinterop.value
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureMetadataOutput
import platform.AVFoundation.AVCaptureMetadataOutputObjectsDelegateProtocol
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureVideoOrientationLandscapeLeft
import platform.AVFoundation.AVCaptureVideoOrientationLandscapeRight
import platform.AVFoundation.AVCaptureVideoOrientationPortrait
import platform.AVFoundation.AVCaptureVideoOrientationPortraitUpsideDown
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVMetadataMachineReadableCodeObject
import platform.AVFoundation.AVMetadataObjectTypeQRCode
import platform.CoreGraphics.CGRect
import platform.Foundation.NSError
import platform.QuartzCore.CALayer
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue

internal class Camera(
    private val onFoundQRCode: CameraQRCodeCallback
): AVCaptureMetadataOutputObjectsDelegateProtocol, NSObject() {

    private val captureSession = AVCaptureSession()

    private var previewLayer: AVCaptureVideoPreviewLayer? = null

    fun prepare(layer: CALayer) {
        val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        if (device == null) {
            println("Device has no camera")
            return
        }

        val videoInput = memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()
            val videoInput = AVCaptureDeviceInput(device = device, error = error.ptr)
            if (error.value != null) {
                println(error.value)
                null
            } else {
                videoInput
            }
        }

        if (videoInput != null && captureSession.canAddInput(videoInput)) {
            captureSession.addInput(videoInput)
        }

        val metadataOutput = AVCaptureMetadataOutput()
        if (captureSession.canAddOutput(metadataOutput)) {
            captureSession.addOutput(metadataOutput)
            metadataOutput.setMetadataObjectsDelegate(this, queue = dispatch_get_main_queue())
            metadataOutput.metadataObjectTypes = listOf(
                AVMetadataObjectTypeQRCode
            )
        }

        previewLayer = AVCaptureVideoPreviewLayer(session = captureSession).also {
            it.frame = layer.bounds
            it.videoGravity = AVLayerVideoGravityResizeAspectFill
            setOrientation(orientation = UIDevice.currentDevice.orientation)
            layer.bounds.useContents {
                println("Bounds: ${size.width}x${size.height}")
            }
            layer.frame.useContents {
                println("Frame: ${size.width}x${size.height}")
            }
            layer.addSublayer(it)
        }

        captureSession.startRunning()
    }

    fun setOrientation(orientation: UIDeviceOrientation) {
        previewLayer?.connection?.videoOrientation = when (orientation) {
            UIDeviceOrientation.UIDeviceOrientationLandscapeLeft ->
                AVCaptureVideoOrientationLandscapeRight
            UIDeviceOrientation.UIDeviceOrientationLandscapeRight ->
                AVCaptureVideoOrientationLandscapeLeft
            UIDeviceOrientation.UIDeviceOrientationPortrait ->
                AVCaptureVideoOrientationPortrait
            UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown ->
                AVCaptureVideoOrientationPortraitUpsideDown
            else -> AVCaptureVideoOrientationPortrait
        }
    }

    override fun captureOutput(
        output: platform.AVFoundation.AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: platform.AVFoundation.AVCaptureConnection
    ) {
        for (didOutputMetadataObject in didOutputMetadataObjects) {
            when (didOutputMetadataObject) {
                is AVMetadataMachineReadableCodeObject -> {
                    didOutputMetadataObject.bounds.useContents {
                        println("Bounds: x=${origin.x}, y=${origin.y}, w=${size.width}, h=${size.height}")
                        didOutputMetadataObject.stringValue?.let { data ->
                            UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleSoft)
                                .impactOccurred()
                            onFoundQRCode(origin.x, origin.y, size.width, size.height, data)
                        }
                    }
                }
            }
        }
    }

    fun setFrame(rect: CValue<CGRect>) {
        previewLayer?.setFrame(rect)
    }

    fun stopRunning() {
        captureSession.stopRunning()
    }
}