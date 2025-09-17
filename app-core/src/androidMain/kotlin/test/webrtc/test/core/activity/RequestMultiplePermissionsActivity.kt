package test.webrtc.test.core.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.toPersistableBundle
import test.webrtc.test.core.ACTION_GRANTED_PERMISSIONS
import test.webrtc.test.core.EXTRA_REQUEST_PERMISSIONS

internal class RequestMultiplePermissionsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringArrayListExtra(EXTRA_REQUEST_PERMISSIONS)?.let {
            requestMultiplePermissions.launch(it.toTypedArray())
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { result ->
        sendBroadcast(Intent(ACTION_GRANTED_PERMISSIONS)
            .putExtra(EXTRA_REQUEST_PERMISSIONS, result.toPersistableBundle()))
        finish()
    }

}