package test.webrtc.test.core.manager

import android.content.Context
import test.webrtc.test.core.receiver.RequestMultiplePermissionsReceiver
import test.webrtc.test.domain.manager.PlatformPermissionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal class PlatformPermissionManagerImpl(
    private val context: Context
) : PlatformPermissionManager,
    RequestMultiplePermissionsReceiver.Callback {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val receiver = RequestMultiplePermissionsReceiver(this)
    private val permissionsCallbacks = mutableSetOf<PlatformPermissionManager.PermissionsCallback>()

    init {
        receiver.register(context)
    }

    override fun registerPermissionsCallback(
        callback: PlatformPermissionManager.PermissionsCallback) {
        permissionsCallbacks.add(callback)
    }

    override fun unregisterPermissionsCallback(
        callback: PlatformPermissionManager.PermissionsCallback) {
        permissionsCallbacks.remove(callback)
    }

    override fun onGrantedPermissions(permissions: Map<String, Boolean>) {

    }
}

