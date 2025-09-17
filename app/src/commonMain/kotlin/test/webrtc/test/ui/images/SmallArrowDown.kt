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

public val AppImages.SmallArrowDown: ImageVector
    get() {
        if (_smallarrowdown != null) {
            return _smallarrowdown!!
        }
        _smallarrowdown = Builder(
            name = "Smallarrowdown", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF98A2B6)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(7.4697f, 9.4697f)
                curveTo(7.7626f, 9.1768f, 8.2374f, 9.1768f, 8.5303f, 9.4697f)
                lineTo(12.0f, 12.9393f)
                lineTo(15.4697f, 9.4697f)
                curveTo(15.7626f, 9.1768f, 16.2374f, 9.1768f, 16.5303f, 9.4697f)
                curveTo(16.8232f, 9.7626f, 16.8232f, 10.2374f, 16.5303f, 10.5303f)
                lineTo(12.5303f, 14.5303f)
                curveTo(12.2374f, 14.8232f, 11.7626f, 14.8232f, 11.4697f, 14.5303f)
                lineTo(7.4697f, 10.5303f)
                curveTo(7.1768f, 10.2374f, 7.1768f, 9.7626f, 7.4697f, 9.4697f)
                close()
            }
        }
            .build()
        return _smallarrowdown!!
    }

private var _smallarrowdown: ImageVector? = null