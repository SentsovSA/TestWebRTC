package test.webrtc.test.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.Typography
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

@Composable
internal fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors(
        primary = Color(0xFF333333)
    ) else lightColors(
        primary = AppColors.Background.Primary
        /*,
        secondary = AppColors.Background.Secondary,
        onSurface = AppColors.GreyControlFill.Default*/
    )
    MaterialTheme(
        colors = colors,
        typography = androidx.compose.material.Typography(InterFont()),
        shapes = Shapes(
            small = RoundedCornerShape(percent = 50),
            medium = RoundedCornerShape(0f),
            large = CutCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 16.dp
            )
        ),
        content = {
            CupertinoTheme(
                content = content,
                colorScheme = lightColorScheme()
            )
            content()
        }
    )

}

@Composable
fun CustomTypography(): Typography {
    val interFontFamily = InterFont()

    return Typography(
        largeTitle = TextStyle(
            fontSize = 34.sp,
            lineHeight = 41.sp,
            fontFamily = InterFont()
        ),
        title1 = TextStyle(
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontFamily = InterFont()
        ),
        title2 = TextStyle(
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = InterFont()
        ),
        title3 = TextStyle(
            fontSize = 20.sp,
            lineHeight = 25.sp,
            fontFamily = InterFont()
        ),
        headline = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = InterFont()
        ),
        body = TextStyle(
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontFamily = InterFont()
        ),
        callout = TextStyle(
            fontSize = 16.sp,
            lineHeight = 21.sp,
            fontFamily = InterFont()
        ),
        subhead = TextStyle(
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontFamily = InterFont()
        ),
        footnote = TextStyle(
            fontSize = 13.sp,
            lineHeight = 18.sp,
            fontFamily = InterFont()
        ),
        caption1 = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = InterFont()
        ),
        caption2 = TextStyle(
            fontSize = 11.sp,
            lineHeight = 13.sp,
            fontFamily = InterFont()
        )
    )
}


private object AppRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.Red

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        pressedAlpha = 0.24f,
        focusedAlpha = 0.24f,
        draggedAlpha = 0.16f,
        hoveredAlpha = 0.08f
    )

}