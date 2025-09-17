package test.webrtc.test.core.manager

import android.app.ActivityManager
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_WEAK
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import androidx.core.content.ContextCompat
import test.webrtc.test.data.repository.SettingsRepository
import test.webrtc.test.domain.manager.BiometryManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class BiometryManagerImpl private constructor(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) : BiometryManager {

    companion object {
        fun create(
            context: Context,
            settingsRepository: SettingsRepository
        ): BiometryManager = BiometryManagerImpl(
            context = context,
            settingsRepository = settingsRepository
        )
    }

    private val activityManager = context.getSystemService(ActivityManager::class.java)

    private val biometricManager = getBiometricManager(context)

    private val requestBiometryCallbacks = mutableSetOf<BiometryManager.RequestBiometryCallback>()

    override fun registerRequestBiometryCallback(callback: BiometryManager.RequestBiometryCallback) {
        requestBiometryCallbacks.add(callback)
    }

    override fun unregisterRequestBiometryCallback(callback: BiometryManager.RequestBiometryCallback) {
        requestBiometryCallbacks.remove(callback)
    }

    override suspend fun isBiometrySet(): Boolean? {
        return settingsRepository.getBiometry()
    }

    override fun isAvailable(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // Для Android 11 (API 30) и выше
                val biometricManager = context.getSystemService(BiometricManager::class.java)
                biometricManager?.canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                // Для Android 10 (API 29)
                val biometricManager = androidx.biometric.BiometricManager.from(context)
                biometricManager.canAuthenticate() == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                // Для Android 6.0 до 9.0 (API 23 - 28)
                val fingerprintManager = ContextCompat.getSystemService(context, FingerprintManager::class.java)
                fingerprintManager?.isHardwareDetected == true && fingerprintManager.hasEnrolledFingerprints()
            }
            else -> {
                false
            }
        }
    }

    override fun request(
        title: String,
        reason: String,
        errorText: String,
        allowCredentials: Boolean,
        isSavingCredentials: Boolean
    ) {
        val executor = ContextCompat.getMainExecutor(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val prompt = BiometricPrompt.Builder(context)
                .setTitle(title)
                .setSubtitle(reason)
                .setNegativeButton(errorText, executor) { dialog, which ->

                }
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
                    prompt.setAllowedAuthenticators(BIOMETRIC_WEAK)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
                    @Suppress("DEPRECATION")
                    prompt.setDeviceCredentialAllowed(allowCredentials)
            }
            prompt.build().authenticate(CancellationSignal(), executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    if(isSavingCredentials) {
                        CoroutineScope(Dispatchers.IO).launch {
                            settingsRepository.setBiometry(true)
                        }.invokeOnCompletion {
                            notifyRequestBiometryCallbacks {
                                onRequestBiometry(true)
                            }
                        }
                    } else {
                        notifyRequestBiometryCallbacks {
                            onRequestBiometry(true)
                        }
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                }
            })
        }
    }

    private fun notifyRequestBiometryCallbacks(
        block: BiometryManager.RequestBiometryCallback.() -> Unit
    ) {
        for (callback in requestBiometryCallbacks) {
            callback.block()
        }
    }

}

fun getBiometricManager(context: Context): Any? {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            context.getSystemService(BiometricManager::class.java)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            ContextCompat.getSystemService(context, FingerprintManager::class.java)
        }
        else -> {
            null
        }
    }
}
