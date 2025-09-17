package test.webrtc.test.filepicker

import test.webrtc.test.context.InternalCalfApi
import test.webrtc.test.context.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.NSURLIsDirectoryKey
import platform.Foundation.dataWithContentsOfURL
import platform.posix.memcpy

/**
 * A wrapper class for a file in the platform-specific implementation.
 *
 * @property url The URL of the file.
 * @property tempUrl The temporary URL of the file,
 * this is used to read the content of the file outside the file picker callback.
 */
actual class KmpFile @InternalCalfApi constructor(
    val url: NSURL,
    internal val tempUrl: NSURL,
) {
    @OptIn(InternalCalfApi::class)
    constructor(url: NSURL) : this(url, url)
}

@OptIn(ExperimentalForeignApi::class)
actual suspend fun KmpFile.readByteArray(context: PlatformContext): ByteArray {
    val data = NSData.dataWithContentsOfURL(tempUrl) ?: return ByteArray(0)
    val byteArraySize: Int = if (data.length > Int.MAX_VALUE.toUInt()) Int.MAX_VALUE else data.length.toInt()
    return ByteArray(byteArraySize).apply {
        usePinned {
            memcpy(it.addressOf(0), data.bytes, data.length)
        }
    }
}

actual fun KmpFile.getName(context: PlatformContext): String? =
    url.absoluteString
        ?.removeSuffix("/")
        ?.split('/')
        ?.lastOrNull()

actual fun KmpFile.getPath(context: PlatformContext): String? =
    url.absoluteString

@OptIn(ExperimentalForeignApi::class)
actual fun KmpFile.isDirectory(context: PlatformContext): Boolean {
    val result = url.resourceValuesForKeys(listOf(NSURLIsDirectoryKey), error = null)
    return result?.get(NSURLIsDirectoryKey) == true
}

/**
 * Checks if the KmpFile exists in the specified platform context.
 *
 * @param context The platform context in which to check the existence of the file.
 * @return True if the file exists, false otherwise.
 */
actual fun KmpFile.exists(context: PlatformContext): Boolean {
    TODO("Not yet implemented")
}