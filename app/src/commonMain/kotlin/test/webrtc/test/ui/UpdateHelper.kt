package test.webrtc.test.ui

import androidx.compose.runtime.Composable

@Composable
expect fun UpdateHelper(isHaveUpdate: @Composable (Boolean) -> Unit)

expect fun updateApp()
