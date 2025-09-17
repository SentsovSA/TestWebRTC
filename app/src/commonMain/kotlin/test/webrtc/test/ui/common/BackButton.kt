package test.webrtc.test.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import test.webrtc.test.ui.navigation.LocalRouterProvider
import test.webrtc.test.ui.theme.AppColors

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val nav = LocalRouterProvider.current
    if (!nav.canPop) return
    IconButton(
        modifier = modifier,
        onClick = {
            nav.pop()
            onClick()
        }
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            tint = AppColors.TextAndIcons.Primary
        )
    }
}

