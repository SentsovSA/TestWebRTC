package test.webrtc.test.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.images.AppImages
import test.webrtc.test.ui.images.Home
import test.webrtc.test.ui.navigation.LocalRouterProvider
import test.webrtc.test.ui.navigation.Route
import test.webrtc.test.ui.theme.AppColors

@Composable
fun BottomNavigation(route: Route) {
    val nav = LocalRouterProvider.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(Color.White)
            .border(1.dp, AppColors.StrokeAndBorder.Border),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in listOf(
            BottomNavigationItem("/home", AppImages.Home, "Главная"),
        )) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        enabled = route.path != item.path,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = AppColors.Brand.TextAndIcons.Default
                        ),
                        onClick = {
                            nav.push(item.path)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val color = if (route.path == item.path)
                        AppColors.Brand.TextAndIcons.Default
                    else
                        AppColors.TextAndIcons.Tertiary
                    Icon(
                        item.icon,
                        tint = color,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        text = item.label,
                        style = MaterialTheme.typography.caption,
                        color = color
                    )
                }
            }
        }
    }
}

private data class BottomNavigationItem(
    val path: String,
    val icon: ImageVector,
    val label: String
)