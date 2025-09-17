package test.webrtc.test.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.theme.AppColors

@Composable
fun ErrorItem(text: String = "При загрузке произошла ошибка. Пожалуйста, обновите страницу.") {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        color = AppColors.Background.Primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            /*Icon(
                AppImages.Alarm,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = null,
                tint = Color.Red
            )*/
            Text(
                text,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                style = MaterialTheme.typography.body2,
                color = AppColors.Critical.TextIcons.Default
            )
        }
    }
}