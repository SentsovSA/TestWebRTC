package test.webrtc.test.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.github.aakira.napier.Napier
import java.io.ByteArrayOutputStream

actual fun compressImage(data: ByteArray): ByteArray {
    Napier.d { "Исходный размер: ${data.size / 1024} KB" }

    val maxWidth = 1280
    val maxHeight = 1280

    val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        ?: return data.also { Napier.e { "Ошибка декодирования изображения" } }

    // Resize до заданных размеров
    val resizedBitmap = resizeBitmap(bitmap, maxWidth, maxHeight)

    val quality = when {
        data.size < 300 * 1024 -> return data
        data.size < 1_000_000 -> 80
        data.size < 2_000_000 -> 60
        data.size < 5_000_000 -> 40
        else -> 25
    }

    val stream = ByteArrayOutputStream()
    val success = resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)

    if (!success) {
        Napier.e { "Не удалось сжать Bitmap" }
        return data
    }

    val result = stream.toByteArray()

    Napier.d {
        """
        Сжатие и обрезание выполнены:
        → Исходный размер: ${data.size / 1024} KB
        → Новый размер: ${result.size / 1024} KB
        → Качество: $quality
        → Размеры: ${resizedBitmap.width} x ${resizedBitmap.height}
        → Экономия: ${100 - (100 * result.size / data.size)}%
        """.trimIndent()
    }

    return result
}


private fun resizeBitmap(src: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = src.width
    val height = src.height

    if (width <= maxWidth && height <= maxHeight) return src

    val aspectRatio = width.toFloat() / height.toFloat()
    val (newWidth, newHeight) = if (aspectRatio > 1f) {
        // Широкое изображение
        maxWidth to (maxWidth / aspectRatio).toInt()
    } else {
        // Высокое изображение
        (maxHeight * aspectRatio).toInt() to maxHeight
    }

    return Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
}
