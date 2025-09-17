package test.webrtc.test.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(
    modifier: Modifier,
    url: String,
    isLongTap: Boolean,
    loading: () -> Unit,
    loaded: () -> Unit
)