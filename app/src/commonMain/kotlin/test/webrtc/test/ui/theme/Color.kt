package test.webrtc.test.ui.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal object AppColors {
    val Fade = Color(0x99000000)
    object Background {
        val Primary = Color(0xFFFFFFFF)
        val Secondary = Color(0xFFF0F2F6)
        val Default = Color(0xFFE6F3FE)
    }
    object TextAndIcons {
        val Primary = Color(0xFF161616)
        val Secondary = Color(0xFF707D97)
        val AccentPrimary = Color(0xFFFFFFFF)
        val AccentColor = Color(0xB3FFFFFF)
        val Tertiary = Color(0xFF98A2B6)
        val Disabled = Color(0xFFCAD0DE)
    }
    object Brand {
        object TextAndIcons {
            val Default = Color(0xFF0086F9)
            val Hover = Color(0xFF006BC7)
            val Tertiary = Color(0xFF005094)
            val Secondary = Color(0x990086F9)
        }
        object Background {
            val Default = Color(0xFFE6F3FE)
            val Hover = Color(0xFFDDEEFD)
            val Pressed = Color(0xFFC9E4FC)
        }
    }
    object StrokeAndBorder {
        val Border = Color(0xFFF1F2F4)
        val Pressed = Color(0xFFF1F2F4)
    }
    object GreyControlFill {
        val Default = Color(0xFFE7E9EC)
        //val Default = Color(0x0A000000)
        val Hover = Color(0x0F000000)
        val Pressed = Color(0x14000000)
        val Disabled = Color(0xFFF4F5F9)
    }
    object WhiteControlFill {
        val Default = Color(0x00FFFFFF)
        val Hover = Color(0x00F5F7FA)
        val Pressed = Color(0x00EFF2F6)
        val Disabled = Color(0x00F4F5F9)
    }
    object ControlBorder {
        val Default = Color(0xFFD1D3E0)
        val Hover = Color(0xFFC6C8D9)
        val Disabled = Color(0xFFEBEDFA)
    }
    object Critical {
        object TextIcons {
            val Default = Color(0xFFDB0000)
            val Hover = Color(0xFFD00B0B)
            val Pressed = Color(0xFFC51616)
        }
    }
    object Warning {
        object TextIcons {
            val Default = Color(0xFFFF9500)
        }
        object Background {
            val Default = Color(0xFFFFF2E0)
        }
    }
    object Success {
        object TextIcons {
            val Default = Color(0xFF19C152)
        }
    }
    @Composable
    fun textField() = TextFieldDefaults.textFieldColors(
        backgroundColor = GreyControlFill.Default,
        focusedIndicatorColor = Brand.TextAndIcons.Default,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = Brand.TextAndIcons.Default
    )
}