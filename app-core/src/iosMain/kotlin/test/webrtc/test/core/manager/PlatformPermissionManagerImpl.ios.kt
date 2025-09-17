package test.webrtc.test.core.manager

import test.webrtc.test.domain.entity.PlatformPermission
import test.webrtc.test.domain.entity.PlatformPermissionStatus
import test.webrtc.test.domain.manager.PlatformPermissionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType
import platform.AVFoundation.AVMediaType
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.authorizationStatusForMediaType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class PlatformPermissionManagerImpl : PlatformPermissionManager {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val permissionsCallbacks = mutableSetOf<PlatformPermissionManager.PermissionsCallback>()

    override fun registerPermissionsCallback(
        callback: PlatformPermissionManager.PermissionsCallback) {
        permissionsCallbacks.add(callback)
    }

    override fun unregisterPermissionsCallback(
        callback: PlatformPermissionManager.PermissionsCallback) {
        permissionsCallbacks.remove(callback)
    }

    override fun status(permission: PlatformPermission) : PlatformPermissionStatus {
        if (permission !is PlatformPermission.IOS)
            return PlatformPermissionStatus.NotImplementedInOS
        return when (permission) {
            PlatformPermission.IOS.AVCaptureDevice.AVMediaTypeVideo -> AVMediaTypeVideo.status()
            PlatformPermission.IOS.AVCaptureDevice.AVMediaTypeAudio -> AVMediaTypeAudio.status()
        }
    }

    override fun request(vararg permissions: PlatformPermission) {
        scope.launch {
            val grantedPermissions = mutableMapOf<PlatformPermission, PlatformPermissionStatus>()
            for (permission in permissions.filterIsInstance<PlatformPermission.IOS>()) {
                grantedPermissions += when (permission) {
                    is PlatformPermission.IOS.AVCaptureDevice -> when (permission) {
                        PlatformPermission.IOS.AVCaptureDevice.AVMediaTypeVideo -> permission.grant(AVMediaTypeVideo)
                        PlatformPermission.IOS.AVCaptureDevice.AVMediaTypeAudio -> permission.grant(AVMediaTypeAudio)
                    }
                }
            }
            notifyPermissionsCallbacks(grantedPermissions)
        }
    }

    private suspend fun <T : PlatformPermission.IOS.AVCaptureDevice> T.grant(
        mediaType: AVMediaType
    ) = suspendCoroutine { continuation ->
        AVCaptureDevice.requestAccessForMediaType(mediaType) { granted ->
            println("$this: $mediaType = $granted")
            continuation.resume(Pair(this, if (granted)
                PlatformPermissionStatus.IOS.AVAuthorization.Authorized
                else
                PlatformPermissionStatus.IOS.AVAuthorization.Denied
            ))
        }
    }

    private fun AVMediaType.status() =
        when (AVCaptureDevice.authorizationStatusForMediaType(this)) {
            AVAuthorizationStatusRestricted -> PlatformPermissionStatus.IOS.AVAuthorization.Restricted
            AVAuthorizationStatusAuthorized -> PlatformPermissionStatus.IOS.AVAuthorization.Authorized
            AVAuthorizationStatusNotDetermined -> PlatformPermissionStatus.IOS.AVAuthorization.NotDetermined
            AVAuthorizationStatusDenied -> PlatformPermissionStatus.IOS.AVAuthorization.Denied
            else -> PlatformPermissionStatus.IOS.AVAuthorization.Unknown
        }


    /*private fun goToSettings() {
        UIApplication.sharedApplication.apply {
            with(NSURL(string = UIApplicationOpenSettingsURLString)) {
                if (canOpenURL(this)) openURL(this)
            }
        }
    }*/

    private suspend fun notifyPermissionsCallbacks(permissions: Map<PlatformPermission, PlatformPermissionStatus>) {
        for (callback in permissionsCallbacks) callback.onPermissions(permissions)
    }

}