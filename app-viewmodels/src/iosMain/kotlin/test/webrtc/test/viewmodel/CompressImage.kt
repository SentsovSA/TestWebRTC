package test.webrtc.test.viewmodel

import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.*
import platform.Foundation.*
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
actual fun compressImage(data: ByteArray): ByteArray {
    val originalSizeKB = data.size / 1024
    Napier.d("Исходный размер: ${originalSizeKB} KB")

    val quality: CGFloat = when {
        data.size < 300 * 1024 -> return data // не трогаем
        data.size < 1_000_000 -> 0.8
        data.size < 2_000_000 -> 0.6
        data.size < 5_000_000 -> 0.4
        else -> 0.25
    }

    val nsData = data.toNSData()
    val originalImage = UIImage(nsData)
    val resizedImage = resizeImage(originalImage, maxWidth = 1280.0, maxHeight = 1280.0)

    val compressed: NSData? = UIImageJPEGRepresentation(resizedImage, 0.8)

    if (compressed == null) {
        println("Ошибка компрессии, возвращаю оригинал")
        return data
    }

    val result = compressed.toByteArray()

    val (resizedImageWidth, resizedImageHeight) = resizedImage.size.useContents { width to height }

    Napier.d(
        """
        Компрессия завершена:
        → Качество: $quality
        → До: ${data.size / 1024} KB
        → После: ${result.size / 1024} KB
        → Экономия: ${100 - (100 * result.size / data.size)}%
        → Размер после resize: ${resizedImageWidth}x${resizedImageHeight}
        """.trimIndent()
    )

    return result
}

@OptIn(ExperimentalForeignApi::class)
fun resizeImage(image: UIImage, maxWidth: Double, maxHeight: Double): UIImage {
    val (originalWidth, originalHeight) = image.size.useContents { width to height }

    if (originalWidth <= maxWidth && originalHeight <= maxHeight) return image

    val aspectRatio = originalWidth / originalHeight
    val (newWidth, newHeight) = if (aspectRatio > 1) {
        maxWidth to (maxWidth / aspectRatio)
    } else {
        (maxHeight * aspectRatio) to maxHeight
    }

    val size = CGSizeMake(newWidth, newHeight)

    UIGraphicsBeginImageContextWithOptions(size, false, 1.0)
    image.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
    val resizedImage = UIGraphicsGetImageFromCurrentImageContext() ?: image
    UIGraphicsEndImageContext()

    return resizedImage
}



@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
    NSData.dataWithBytes(pinned.addressOf(0), size.toULong())
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).also { byteArray ->
    byteArray.usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}