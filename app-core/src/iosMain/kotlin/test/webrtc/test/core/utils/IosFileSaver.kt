package test.webrtc.test.core.utils

import test.webrtc.test.core.interfaces.FileSaver
import test.webrtc.test.core.interfaces.SavedFile
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSFileSize
import platform.Foundation.NSFileModificationDate
import platform.Foundation.writeToURL

private const val NSDocumentDirectory: ULong = 9U
private const val NSUserDomainMask: ULong = 1U

class IOSFileSaver : FileSaver {

    private fun getZkUslugiDir(fileType: FileType): NSURL? {
        val dir = when(fileType) {
            FileType.ALL -> "ZKUslugi"
            FileType.DOCUMENT -> "ZKUslugi/Documents"
            FileType.STATEMENT -> "ZKUslugi/Statements"
        }
        val fileManager = NSFileManager.defaultManager
        val urls = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask)
        val documentsUrl = urls.first() as? NSURL ?: return null

        val zkDir = documentsUrl.URLByAppendingPathComponent(dir)
        if (zkDir != null) {
            if (!fileManager.fileExistsAtPath(zkDir.path!!)) {
                fileManager.createDirectoryAtURL(zkDir, withIntermediateDirectories = true, attributes = null, error = null)
            }
        }
        return zkDir
    }

    override fun saveFile(bytes: ByteArray, fileName: String, fileType: FileType): String {
        val dir = getZkUslugiDir(fileType) ?: error("Failed to get ZKUslugi dir")

        val fileUrl = dir.URLByAppendingPathComponent(fileName)
        val data = bytes.toNSData()

        if (!fileUrl?.let { data.writeToURL(it, atomically = true) }!!) {
            error("Failed to write file")
        }

        return fileUrl.path!!
    }

    override fun listSavedFiles(fileType: FileType): List<SavedFile> {
        val dir = getZkUslugiDir(fileType) ?: return emptyList()
        val fileManager = NSFileManager.defaultManager

        val files = fileManager.contentsOfDirectoryAtPath(dir.path!!, null) ?: return emptyList()

        return (files as List<*>).mapNotNull { nameObj ->
            val name = nameObj as? String ?: return@mapNotNull null
            val path = dir.URLByAppendingPathComponent(name)?.path!!
            val attrs = fileManager.attributesOfItemAtPath(path, null) ?: return@mapNotNull null

            val size = (attrs[NSFileSize] as? NSNumber)?.longLongValue ?: 0L
            val modified = (attrs[NSFileModificationDate] as? NSDate)?.timeIntervalSince1970?.times(1000)?.toLong() ?: 0L

            SavedFile(name = name, path = path, size = size, lastModified = modified)
        }
    }

    override fun deleteFile(fileId: Int, fileType: FileType): Boolean {
        val dir = getZkUslugiDir(fileType) ?: return false
        val fileManager = NSFileManager.defaultManager

        val files = fileManager.contentsOfDirectoryAtPath(dir.path!!, null) ?: return false
        val match = (files as List<*>).firstOrNull {
            val name = it as? String
            name?.contains("_$fileId") == true
        } ?: return false

        val fileUrl = dir.URLByAppendingPathComponent(match as String)
        return fileUrl?.let { fileManager.removeItemAtURL(it, null) } ?: false
    }

    @OptIn(ExperimentalForeignApi::class)
    fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
        NSData.dataWithBytes(pinned.addressOf(0), size.toULong())
    }
}

