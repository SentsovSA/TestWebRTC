package test.webrtc.test.domain.manager

import kotlinx.coroutines.flow.StateFlow

interface ThemeManager {
    val isDarkTheme: StateFlow<Boolean>
}