package test.webrtc.test.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.theme.AppColors
import io.github.alexzhirkevich.cupertino.CupertinoBorderedTextField
import io.github.alexzhirkevich.cupertino.CupertinoBorderedTextFieldDefaults

@Composable
actual fun SmsCodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
    textStyle: TextStyle,
    containerColor: Color,
    borderColor: Color,
    unfocusedBorderColor: Color,
    onFinish: (String) -> Unit,
) {
    CupertinoBorderedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        textStyle = MaterialTheme.typography.body1.copy(
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            color = AppColors.TextAndIcons.Primary
        ),
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        contentAlignment = Alignment.Bottom,
        colors = CupertinoBorderedTextFieldDefaults.colors(
            focusedTextColor = AppColors.TextAndIcons.Primary,
            focusedContainerColor = AppColors.GreyControlFill.Default,
            unfocusedContainerColor = AppColors.GreyControlFill.Default,
            focusedBorderColor = AppColors.Brand.TextAndIcons.Default,
            unfocusedBorderColor = Color.Transparent
        )
    )
}