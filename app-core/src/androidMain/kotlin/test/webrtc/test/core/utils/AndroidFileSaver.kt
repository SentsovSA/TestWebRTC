package test.webrtc.test.core.utils

import android.content.Context
import android.os.Environment
import test.webrtc.test.core.interfaces.FileSaver
import test.webrtc.test.core.interfaces.SavedFile
import test.webrtc.test.domain.enum.FileType
import java.io.File

class AndroidFileSaver(private val context: Context) : FileSaver {

    override fun saveFile(bytes: ByteArray, fileName: String, fileType: FileType): String {
        val directoryByType = when (fileType) {
            FileType.ALL -> "ZKUslugi"
            FileType.DOCUMENT -> "ZKUslugi/Documents"
            FileType.STATEMENT -> "ZKUslugi/Statements"
        }
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            directoryByType
        )
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, fileName)
        file.writeBytes(bytes)

        return file.absolutePath
    }

    override fun listSavedFiles(fileType: FileType): List<SavedFile> {
        val directoryByType = when (fileType) {
            FileType.ALL -> "ZKUslugi"
            FileType.DOCUMENT -> "ZKUslugi/Documents"
            FileType.STATEMENT -> "ZKUslugi/Statements"
        }
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            directoryByType
        )
        if (!dir.exists() || !dir.isDirectory) return emptyList()

        return dir.listFiles()?.map {
            SavedFile(
                name = it.name,
                path = it.absolutePath,
                size = it.length(),
                lastModified = it.lastModified()
            )
        } ?: emptyList()
    }

    override fun deleteFile(fileId: Int, fileType: FileType): Boolean {
        val directoryByType = when (fileType) {
            FileType.ALL -> "ZKUslugi"
            FileType.DOCUMENT -> "ZKUslugi/Documents"
            FileType.STATEMENT -> "ZKUslugi/Statements"
        }
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            directoryByType
        )
        if (!dir.exists()) return false

        val target = dir.listFiles()?.firstOrNull {
            it.name.contains("_$fileId")
        }

        return target?.delete() ?: false
    }
}