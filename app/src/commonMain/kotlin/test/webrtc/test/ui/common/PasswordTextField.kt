package test.webrtc.test.ui.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.*
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    initialValue: String? = null,
    focusRequester: FocusRequester = FocusRequester(),
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String = "",
    filter: ((String) -> Boolean)? = null,
    onValueChange: (String) -> Unit
) {
    var visibility by remember { mutableStateOf(false) }
    AppTextField(
        modifier = modifier,
        initialValue = initialValue ?: "",
        focusRequester = focusRequester,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = placeholder,
        //leadingIcon = FontAwesomeIcons.Solid.Key,
        trailingIcon = { value ->
            if (value.isNotEmpty())
                with(FontAwesomeIcons.Solid) { if (visibility) EyeSlash else Eye }
            else null
        },
        onClickTrailingIcon = {
            visibility = !visibility
        },
        singleLine = true,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions
    )
}