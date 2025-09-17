package test.webrtc.test.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.theme.AppColors
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIColor
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UIKeyboardTypeNumberPad
import platform.UIKit.UITextBorderStyle
import platform.UIKit.UITextContentTypeOneTimeCode
import platform.UIKit.UITextField
import platform.UIKit.UITextFieldDelegateProtocol
import platform.darwin.NSObject

private val textFieldDelegate = mutableMapOf<UITextField, NSObject>()

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
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
    var border by remember { mutableStateOf(Color.Transparent) }
    var isFocused by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }
    LaunchedEffect(code) {
        if (code.length == 4) {
            onFinish(code)
        }
    }
    UIKitView(
        factory = {
            val textField = UITextField().apply {
                this.text = value
                this.placeholder = placeholder
                this.keyboardType = UIKeyboardTypeNumberPad
                this.textContentType = UITextContentTypeOneTimeCode
                this.textColor = UIColor(
                    red = 22.0 / 255.0,
                    green = 22.0 / 255.0,
                    blue = 22.0 / 255.0,
                    alpha = 1.0
                )
                this.backgroundColor = UIColor(
                    red = 0xE7 / 255.0,
                    green = 0xE9 / 255.0,
                    blue = 0xEC / 255.0,
                    alpha = 1.0
                )
                this.borderStyle = UITextBorderStyle.UITextBorderStyleRoundedRect
            }
            val delegate = object : NSObject(), UITextFieldDelegateProtocol {
                @ObjCAction
                fun textDidChange(sender: UITextField) {
                    val enteredText = sender.text.orEmpty()
                    onValueChange(enteredText)
                }
            }

            textFieldDelegate[textField] = delegate

            val selector = NSSelectorFromString("textDidChange:")
            textField.addTarget(
                delegate,
                action = selector,
                forControlEvents = UIControlEventEditingChanged
            )

            textField.becomeFirstResponder()

            textField
        },
        update = {
            it.text = value
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, border, RoundedCornerShape(8.dp))
            .onFocusChanged {
                isFocused = it.isFocused
                border =
                    if (it.isFocused) AppColors.Brand.TextAndIcons.Default else Color.Transparent
            },
        background = Color.Transparent
    )
}
