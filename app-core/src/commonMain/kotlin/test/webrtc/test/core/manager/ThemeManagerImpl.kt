package test.webrtc.test.core.manager

import test.webrtc.test.data.repository.SettingsRepository
import test.webrtc.test.domain.manager.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ThemeManagerImpl private constructor(
    private val settingsRepository: SettingsRepository
) : ThemeManager, CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.IO) {

    companion object {
        fun create(
            settingsRepository: SettingsRepository
        ) : ThemeManager = ThemeManagerImpl(
            settingsRepository = settingsRepository
        )
    }

    private val _isDarkTheme = MutableStateFlow(false)
    override val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        launch {

        }
    }

}