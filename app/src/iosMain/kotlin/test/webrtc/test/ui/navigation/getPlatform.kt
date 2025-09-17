package test.webrtc.test.ui.navigation

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.cValue
import kotlinx.cinterop.refTo
import platform.UIKit.UIDevice
import platform.darwin.sysctlbyname

actual fun getPlatform(): Platform = Platform.iOS

actual fun getDeviceInfo(): String {
    return "Устройство: ${UIDevice.currentDevice.model}.\nОС: ${UIDevice.currentDevice.systemName} ${UIDevice.currentDevice.systemVersion}"
}

@OptIn(ExperimentalForeignApi::class)
private fun getDeviceModel(): String {
    val buffer = ByteArray(128)
    val size = cValue<ULongVar>().also {
        sysctlbyname("hw.machine", buffer.refTo(0), it, null, 0u)
    }

    val machine = buffer.decodeToString(endIndex = size.size)

    return deviceNameFromMachine(machine) ?: "Unknown device ($machine)"
}

private fun deviceNameFromMachine(machine: String): String? {
    return when (machine) {
        "iPhone13,4" -> "iPhone 12 Pro Max"
        "iPhone13,2" -> "iPhone 12"
        "iPad11,6" -> "iPad 8th Gen"
        "iPhone14,5" -> "iPhone 13 Mini"
        "iPhone14,2" -> "iPhone 13 Pro"
        else -> null
    }
}

/*@OptIn(ExperimentalForeignApi::class)
fun getMachineIdentifier(): String {
    memScoped {
        val bufferSize = alloc<size_tVar>()
        sysctlbyname("hw.machine", null, bufferSize.ptr, null, 0) // Узнаем размер данных

        if (bufferSize.value <= 0) {
            return "Failed to retrieve machine identifier (invalid size)"
        }
        val buffer = ByteArray(bufferSize.value.toInt())
        val result = sysctlbyname("hw.machine", buffer.refTo(0), bufferSize.ptr, null, 0)
        if (result != 0) {
            return "Failed to retrieve machine identifier (error code: $result)"
        }
        return buffer.decodeToString(endIndex = bufferSize.value.toInt())
    }
}*/
