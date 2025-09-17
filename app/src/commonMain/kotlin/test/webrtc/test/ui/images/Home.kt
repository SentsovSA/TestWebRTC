package test.webrtc.test.ui.images

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppImages.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home = Builder(name = "Home", defaultWidth = 18.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 18.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF0086F9)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.6374f, 1.2629f)
                curveTo(8.3209f, 0.5782f, 9.429f, 0.5793f, 10.1123f, 1.2627f)
                lineTo(16.8193f, 7.9697f)
                curveTo(17.3348f, 8.4852f, 17.625f, 9.1849f, 17.625f, 9.914f)
                verticalLineTo(18.5f)
                curveTo(17.625f, 18.9142f, 17.2892f, 19.25f, 16.875f, 19.25f)
                horizontalLineTo(11.375f)
                curveTo(10.9608f, 19.25f, 10.625f, 18.9142f, 10.625f, 18.5f)
                verticalLineTo(14.0f)
                curveTo(10.625f, 13.0332f, 9.8418f, 12.25f, 8.875f, 12.25f)
                curveTo(7.9082f, 12.25f, 7.125f, 13.0332f, 7.125f, 14.0f)
                verticalLineTo(18.5f)
                curveTo(7.125f, 18.9142f, 6.7892f, 19.25f, 6.375f, 19.25f)
                horizontalLineTo(0.875f)
                curveTo(0.4608f, 19.25f, 0.125f, 18.9142f, 0.125f, 18.5f)
                verticalLineTo(9.914f)
                curveTo(0.125f, 9.1849f, 0.4152f, 8.4852f, 0.9307f, 7.9697f)
                lineTo(7.6374f, 1.2629f)
                curveTo(7.6375f, 1.2628f, 7.6373f, 1.2631f, 7.6374f, 1.2629f)
                close()
                moveTo(9.0517f, 2.3233f)
                curveTo(8.9532f, 2.2248f, 8.7956f, 2.2258f, 8.699f, 2.3227f)
                lineTo(1.9913f, 9.0303f)
                curveTo(1.7568f, 9.2648f, 1.625f, 9.5831f, 1.625f, 9.914f)
                verticalLineTo(17.75f)
                horizontalLineTo(5.625f)
                verticalLineTo(14.0f)
                curveTo(5.625f, 12.2048f, 7.0798f, 10.75f, 8.875f, 10.75f)
                curveTo(10.6702f, 10.75f, 12.125f, 12.2048f, 12.125f, 14.0f)
                verticalLineTo(17.75f)
                horizontalLineTo(16.125f)
                verticalLineTo(9.914f)
                curveTo(16.125f, 9.5831f, 15.9932f, 9.2648f, 15.7587f, 9.0303f)
                lineTo(9.0517f, 2.3233f)
                close()
            }
        }
        .build()
        return _home!!
    }

private var _home: ImageVector? = null
