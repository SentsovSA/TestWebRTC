package test.webrtc.test.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.images.AppImages
import test.webrtc.test.ui.images.SmallArrowDown
import test.webrtc.test.ui.theme.AppColors
import test.webrtc.test.ui.utils.click

@Composable
fun PanelAboveKeyboard(
    isKeyboardVisible: Boolean,
    isOffsetNeeded: Boolean = true,
    onKeyboardVisibilityChange: (Boolean) -> Unit,
) {
    var insetsBottom by remember { mutableStateOf(0) }
    insetsBottom = WindowInsets.ime.getBottom(LocalDensity.current)
    val keyboardController = LocalSoftwareKeyboardController.current
    val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    AnimatedVisibility(
        visible = isKeyboardVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        val offsetModifier =
            if (isOffsetNeeded) Modifier.offset { IntOffset(0, -insetsBottom) } else Modifier
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(offsetModifier)
                .border(1.dp, AppColors.Brand.TextAndIcons.Default, shape)
                .background(AppColors.Background.Primary, shape)
                .clip(shape)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                        .click {
                            keyboardController?.hide()
                            onKeyboardVisibilityChange(false)
                        }
                ) {
                    Icon(
                        AppImages.SmallArrowDown,
                        contentDescription = null,
                        tint = AppColors.Brand.TextAndIcons.Default,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Text(
                    text = "Сохранить",
                    color = AppColors.Brand.TextAndIcons.Default,
                    modifier = Modifier
                        .padding(8.dp)
                        .click {
                            keyboardController?.hide()
                            onKeyboardVisibilityChange(false)
                        }
                )
            }
        }
    }
}