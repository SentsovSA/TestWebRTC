package test.webrtc.test.filepicker

import androidx.compose.ui.InternalComposeUiApi
import test.webrtc.test.context.PlatformContext

/**
 * A wrapper class for a file in the platform specific implementation.
 */
expect class KmpFile

/**
 * Checks if the KmpFile exists in the specified platform context.
 *
 * @param context The platform context in which to check the existence of the file.
 * @return True if the file exists, false otherwise.
 */
@OptIn(InternalComposeUiApi::class)
expect fun KmpFile.exists(context: PlatformContext): Boolean

/**
 * Reads the content of the KmpFile as a byte array.
 *
 * @param context The platform context.
 * @return The content of the file as a byte array.
 */
@OptIn(InternalComposeUiApi::class)
expect suspend fun KmpFile.readByteArray(context: PlatformContext): ByteArray

/**
 * Reads the name of the KmpFile.
 *
 * @param context The platform context.
 * @return The name of the file as a string.
 */
@OptIn(InternalComposeUiApi::class)
expect fun KmpFile.getName(context: PlatformContext): String?

/**
 * Reads the path of the KmpFile.
 *
 * @param context The platform context.
 * @return The path of the file as a string.
 */
@OptIn(InternalComposeUiApi::class)
expect fun KmpFile.getPath(context: PlatformContext): String?

/**
 * Checks if the KmpFile is a directory.
 *
 * @param context The platform context.
 * @return True if the file is a directory, false otherwise.
 */
@OptIn(InternalComposeUiApi::class)
expect fun KmpFile.isDirectory(context: PlatformContext): Boolean

/**
 * Checks if the KmpFile is a file.
 *
 * @param context The platform context.
 * @return True if the file is a file, false otherwise.
 */
@OptIn(InternalComposeUiApi::class)
fun KmpFile.isFile(context: PlatformContext): Boolean = !isDirectory(context)