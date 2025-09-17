package test.webrtc.test.domain.manager

interface PinCodeManager {

    suspend fun isLocked(): Boolean

    suspend fun createOrUpdate(code: List<Int>, verification: List<Int>) : CreateOrUpdateStatus

    suspend fun verification(code: List<Int>) : VerificationStatus

    enum class CreateOrUpdateStatus {
        VerificationFailed,
        Equal,
        Success
    }

    enum class VerificationStatus {
        Failed,
        Success
    }

}