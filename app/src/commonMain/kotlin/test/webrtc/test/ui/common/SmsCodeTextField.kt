package test.webrtc.test.ui.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import test.webrtc.test.ui.theme.AppColors

@Composable
expect fun SmsCodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Введите код из SMS",
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        color = AppColors.TextAndIcons.Primary
    ),
    containerColor: Color = AppColors.GreyControlFill.Default,
    borderColor: Color = AppColors.Brand.TextAndIcons.Default,
    unfocusedBorderColor: Color = Color.Transparent,
    onFinish: (String) -> Unit,
)