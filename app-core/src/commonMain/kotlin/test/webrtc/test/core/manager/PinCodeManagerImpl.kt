package test.webrtc.test.core.manager

import test.webrtc.test.data.repository.SettingsRepository
import test.webrtc.test.domain.manager.PinCodeManager

internal class PinCodeManagerImpl private constructor(
    private val settingsRepository: SettingsRepository
) : PinCodeManager {

    companion object {
        fun create(
            settingsRepository: SettingsRepository
        ) : PinCodeManager = PinCodeManagerImpl(
            settingsRepository = settingsRepository
        )
    }

    override suspend fun isLocked() = settingsRepository.getPiCode() != null

    override suspend fun createOrUpdate(
        code: List<Int>,
        verification: List<Int>
    ) : PinCodeManager.CreateOrUpdateStatus {
        if (code != verification)
            return PinCodeManager.CreateOrUpdateStatus.VerificationFailed
        val current = getPinCode()
        if (current == code)
            return PinCodeManager.CreateOrUpdateStatus.Equal
        settingsRepository.setPiCode(code.joinToString(""))
        return PinCodeManager.CreateOrUpdateStatus.Success
    }

    override suspend fun verification(code: List<Int>): PinCodeManager.VerificationStatus {
        val current = getPinCode()
        return if (code == current)
            PinCodeManager.VerificationStatus.Success
        else
            PinCodeManager.VerificationStatus.Failed
    }

    private suspend fun getPinCode() : List<Int> {
        val code = settingsRepository.getPiCode()
        if (code.isNullOrBlank()) return listOf()
        return code.toCharArray().map { it.toString().toInt() }
    }

}