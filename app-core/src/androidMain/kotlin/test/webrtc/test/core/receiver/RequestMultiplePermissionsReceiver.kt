package test.webrtc.test.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.PersistableBundle
import androidx.core.content.ContextCompat
import test.webrtc.test.core.ACTION_GRANTED_PERMISSIONS
import test.webrtc.test.core.EXTRA_REQUEST_PERMISSIONS

internal class RequestMultiplePermissionsReceiver(
    private val callback: Callback
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_GRANTED_PERMISSIONS) return
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(EXTRA_REQUEST_PERMISSIONS, PersistableBundle::class.java)
        else
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_REQUEST_PERMISSIONS) as? PersistableBundle?)?.let {
            val map = mutableMapOf<String, Boolean>()
            for (key in it.keySet()) map[key] = it.getBoolean(key)
            callback.onGrantedPermissions(map)
        }
    }

    fun register(context: Context) {
        ContextCompat.registerReceiver(context, this,
            IntentFilter().also {
                it.addAction(ACTION_GRANTED_PERMISSIONS)
            }, ContextCompat.RECEIVER_EXPORTED)
    }

    interface Callback {
        fun onGrantedPermissions(permissions: Map<String, Boolean>)
    }

}