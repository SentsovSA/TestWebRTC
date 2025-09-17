package test.webrtc.test.domain.manager

interface PlatformPermissionManager {

    fun registerPermissionsCallback(callback: PermissionsCallback)
    fun unregisterPermissionsCallback(callback: PermissionsCallback)

    interface PermissionsCallback {

    }

}