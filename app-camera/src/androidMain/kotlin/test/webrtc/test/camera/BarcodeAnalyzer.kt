package test.webrtc.test.camera

import android.graphics.Rect
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

internal class BarcodeAnalyzer(
    private val onFoundQRCode: CameraQRCodeCallback
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            //Barcode.FORMAT_CODABAR,
            //Barcode.FORMAT_CODE_39,
            //Barcode.FORMAT_CODE_93,
            //Barcode.FORMAT_CODE_128,
            //Barcode.FORMAT_EAN_8,
            //Barcode.FORMAT_EAN_13,
            //Barcode.FORMAT_ITF,
            //Barcode.FORMAT_UPC_E,
            //Barcode.FORMAT_AZTEC,
            //Barcode.FORMAT_DATA_MATRIX,
            //Barcode.FORMAT_PDF417,
            Barcode.FORMAT_QR_CODE
        )
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            scanner.process(InputImage.fromMediaImage(
                image, imageProxy.imageInfo.rotationDegrees)
            ).addOnSuccessListener { items ->
                for (item in items) {
                    val rect = item.boundingBox ?: Rect()
                    onFoundQRCode(
                        rect.left.toDouble(),
                        rect.top.toDouble(),
                        rect.width().toDouble(),
                        rect.height().toDouble(),
                        item.rawValue ?: item.displayValue ?: "null"
                    )
                }
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}