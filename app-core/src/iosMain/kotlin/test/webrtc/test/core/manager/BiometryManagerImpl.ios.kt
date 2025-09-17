package test.webrtc.test.core.manager

import test.webrtc.test.data.repository.SettingsRepository
import test.webrtc.test.domain.manager.BiometryManager
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import platform.Foundation.NSError
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

internal class BiometryManagerImpl private constructor(
    private val settingsRepository: SettingsRepository
) : BiometryManager {

    companion object {
        fun create(
            settingsRepository: SettingsRepository
        ): BiometryManager = BiometryManagerImpl(
            settingsRepository = settingsRepository
        )
    }

    private val requestBiometryCallbacks = mutableSetOf<BiometryManager.RequestBiometryCallback>()

    override fun registerRequestBiometryCallback(callback: BiometryManager.RequestBiometryCallback) {
        requestBiometryCallbacks.add(callback)
    }

    override fun unregisterRequestBiometryCallback(callback: BiometryManager.RequestBiometryCallback) {
        requestBiometryCallbacks.remove(callback)
    }

    override fun isAvailable(): Boolean {
        return LAContext().canEvaluatePolicy(
            policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
            error = null
        )
    }

    override suspend fun isBiometrySet(): Boolean? {
        return settingsRepository.getBiometry()
    }

    override fun request(
        title: String,
        reason: String,
        errorText: String,
        allowCredentials: Boolean,
        isSavingCredentials: Boolean
    ) {
        val context = LAContext()
        context.setLocalizedFallbackTitle(errorText)

        val policy = if (allowCredentials)
            LAPolicyDeviceOwnerAuthentication
        else
            LAPolicyDeviceOwnerAuthenticationWithBiometrics

        val (canEvaluate, evaluateError) = memScoped {
            val p = alloc<ObjCObjectVar<NSError?>>()
            val canEvaluate = runCatching {
                context.canEvaluatePolicy(policy, error = p.ptr)
            }.getOrNull()
            canEvaluate to p.value
        }

        if (evaluateError != null) throw Exception(evaluateError.description())
        if (canEvaluate == null) return

        context.evaluatePolicy(
            policy = policy,
            localizedReason = "Введите код-пароль",
            reply = { result, error ->
                if (error != null) {
                    notifyRequestBiometryCallbacks {
                        onRequestBiometryError(message = error.description())
                    }
                } else {
                    if (isSavingCredentials) {
                        CoroutineScope(Dispatchers.IO).launch {
                            settingsRepository.setBiometry(true)
                        }.invokeOnCompletion {
                            notifyRequestBiometryCallbacks {
                                onRequestBiometry(true)
                            }
                        }
                    } else {
                        notifyRequestBiometryCallbacks {
                            onRequestBiometry(result)
                        }
                    }
                }
            }
        )
    }

    private fun notifyRequestBiometryCallbacks(
        block: BiometryManager.RequestBiometryCallback.() -> Unit
    ) {
        for (callback in requestBiometryCallbacks) {
            callback.block()
        }
    }

}