package test.webrtc.test.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import test.webrtc.test.AndroidUpdateHelper
import io.kamel.core.applicationContext

@Composable
actual fun UpdateHelper(isHaveUpdate: @Composable (Boolean) -> Unit) {
    val context = LocalContext.current
    val androidUpdateHelper = AndroidUpdateHelper(context)
    var result by remember { mutableStateOf<Boolean?>(null) }

    androidUpdateHelper.isHaveUpdate {
        result = it
    }
    result?.let {
        isHaveUpdate(it)
    }
}

actual fun updateApp() {
    val context = applicationContext
    val androidUpdateHelper = AndroidUpdateHelper(context)

    androidUpdateHelper.openStore()
}
