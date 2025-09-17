package test.webrtc.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AndroidUpdateHelper(private val context: Context) {

    fun isHaveUpdate(resultCheck: (Boolean) -> Unit) {
        val coroutineScope = CoroutineScope(SupervisorJob())
        coroutineScope.launch {
            val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)
            val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    if (appUpdateInfo.availableVersionCode() > getBundleVersion().toInt())
                        resultCheck(true)
                    else
                        resultCheck(false)
                } else {
                    resultCheck(false)
                }
            }
            appUpdateInfoTask.addOnFailureListener {
                resultCheck(false)
            }
        }
    }

    fun openStore() {
        val appId = context.packageName
        val rateIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=$appId")
        )
        var marketFound = false

        val otherApps = context.packageManager.queryIntentActivities(rateIntent, 0)
        for (otherApp in otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName
                == "com.android.vending"
            ) {
                val otherAppActivity = otherApp.activityInfo
                val componentName = ComponentName(
                    otherAppActivity.applicationInfo.packageName,
                    otherAppActivity.name
                )
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                rateIntent.setComponent(componentName)
                context.startActivity(rateIntent)
                marketFound = true
                break
            }
        }


        // if GP not present on device, open web browser
        if (!marketFound) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appId")
            )
            context.startActivity(webIntent)
        }
    }

    fun getBundleVersion(): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val versionCode = pInfo.versionCode
            val major = versionCode / 10000
            val minor = (versionCode % 10000) / 100
            val patch = versionCode % 100
            return "$major$minor$patch"
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}