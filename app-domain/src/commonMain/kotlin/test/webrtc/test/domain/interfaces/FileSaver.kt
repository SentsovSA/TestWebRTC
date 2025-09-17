package test.webrtc.test.domain.interfaces

import test.webrtc.test.domain.enum.FileType

interface FileSaver {
    fun saveFile(bytes: ByteArray, fileName: String, fileType: FileType): String
    fun listSavedFiles(fileType: FileType): List<SavedFile>
    fun deleteFile(fileId: Int, fileType: FileType): Boolean
}

data class SavedFile(
    val name: String,
    val path: String,
    val size: Long,
    val lastModified: Long
)