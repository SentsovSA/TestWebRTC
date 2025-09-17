package test.webrtc.test.domain.manager

interface BiometryManager {

    suspend fun isBiometrySet(): Boolean?

    fun isAvailable() : Boolean

    fun request(
        title: String,
        reason: String,
        errorText: String,
        allowCredentials: Boolean,
        isSavingCredentials: Boolean
    )

    fun registerRequestBiometryCallback(callback: RequestBiometryCallback)
    fun unregisterRequestBiometryCallback(callback: RequestBiometryCallback)

    interface RequestBiometryCallback {
        fun onRequestBiometry(status: Boolean)
        fun onRequestBiometryError(message: String?)
    }

}