package test.webrtc.test.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun UpdateHelper(isHaveUpdate: @Composable (Boolean) -> Unit) {
    val currentVersion = getBundleVersion()
    val resultState = remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        val url = NSURL.URLWithString("https://itunes.apple.com/lookup?id=${6670192414}")
        val session = NSURLSession.sharedSession
        val dataTask = session.dataTaskWithURL(url!!) { data, response, error ->
            if (error != null || data == null) {
                resultState.value = false
                return@dataTaskWithURL
            }

            val jsonString = NSString.create(data, NSUTF8StringEncoding)?.toString()
            if (jsonString == null) {
                println("Error converting data to string")
                resultState.value = false
                return@dataTaskWithURL
            }

            val jsonData = NSJSONSerialization.JSONObjectWithData(
                data,
                NSJSONReadingAllowFragments,
                null
            ) as? Map<String, Any>

            val results = jsonData?.get("results") as? List<Map<String, Any>>
            val appStoreVersion = results?.firstOrNull()?.get("version") as? String

            if (appStoreVersion != null) {
                val appStoreVerInt = semverToInt(appStoreVersion)
                val currentVersionInt = semverToInt(currentVersion)

                resultState.value = appStoreVerInt > currentVersionInt
            } else {
                println("Error parsing version from JSON")
                resultState.value = false
            }
        }
        dataTask.resume()
    }

    resultState.value?.let {
        isHaveUpdate(it)
    }
}


actual fun updateApp() {
    val url = NSURL.URLWithString("itms-apps://itunes.apple.com/app/id6670192414")
    UIApplication.sharedApplication.openURL(url!!, options = mapOf<Any?, String>()) {}
}

fun semverToInt(version: String): Int {
    val parts = version.split(".")
        .map { it.toIntOrNull() ?: 0 }

    val major = parts.getOrElse(0) { 0 } * 10000
    val minor = parts.getOrElse(1) { 0 } * 100
    val patch = parts.getOrElse(2) { 0 }

    return major + minor + patch
}


private fun getBundleVersion(): String {
    val infoDictionary = NSBundle.mainBundle.infoDictionary
    val currentVersion = infoDictionary?.getValue("CFBundleShortVersionString").toString()
    return currentVersion
}
