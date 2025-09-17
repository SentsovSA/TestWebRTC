package test.webrtc.test.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.navigation.BackHandler
import test.webrtc.test.ui.theme.AppColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    enabled: Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions? = null,
    onDoneAction: (String) -> String = { it },
    onSendAction: (String) -> String = { it },
    placeholder: String = "",
    singleLine: Boolean = true,
    autofocus: Boolean = false,
    clearFocusOnBackPressed: Boolean = true,
    maxLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    borderShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: ImageVector? = null,
    leadingIconColor: Color = AppColors.TextAndIcons.Tertiary,
    trailingIcon: ((String) -> ImageVector?)? = null,
    trailingIconColor: Color = AppColors.TextAndIcons.Primary,
    onClickTrailingIcon: (() -> Unit)? = null,
    filter: ((String) -> Boolean)? = null,
    onFocus: ((Boolean) -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    label: String = "",
    deleteNeeded: Boolean = true,
    onSelectLoginType: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    var border by remember { mutableStateOf(Color.Transparent) }
    var isFocused by remember { mutableStateOf(false) }
    val (value, setValue) = remember(initialValue) { mutableStateOf(initialValue) }
    val loginTypeState by rememberUpdatedState(onSelectLoginType)
    var isFirstLaunch by remember { mutableStateOf(true) }
    LaunchedEffect(loginTypeState) {
        if (isFirstLaunch) {
            isFirstLaunch = false
            return@LaunchedEffect
        }
        setValue("")
        onValueChange("")
    }
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                if (event.key == Key.Backspace) {
                    if (value.isNotEmpty()) {
                        setValue(value.replace(value, value.dropLast(1)))
                    }
                    return@onKeyEvent true
                } else {
                    return@onKeyEvent false
                }
            }
            .onFocusChanged {
                isFocused = it.isFocused
                onFocus?.invoke(it.isFocused)
                border =
                    if (it.isFocused) AppColors.Brand.TextAndIcons.Default else Color.Transparent
            }.then(modifier),
        readOnly = readOnly,
        value = value,
        enabled = enabled,
        cursorBrush = SolidColor(AppColors.Brand.TextAndIcons.Default),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions(
            onSend = {
                setValue(onSendAction(value))
            },
            onDone = {
                setValue(onDoneAction(value))
            }
        ),
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.body1.copy(
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            color = AppColors.TextAndIcons.Primary
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        onValueChange = onValueChange@{
            if (it.length <= maxLength) {
                if (filter != null && !filter(it))
                    return@onValueChange
                setValue(it)
                onValueChange(it)
            }
        }
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .height(56.dp)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = border
                    ),
                    shape = borderShape
                )
                .background(
                    color = AppColors.GreyControlFill.Default,
                    shape = borderShape
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val horizontalPadding = 14.dp
            val trailingIconPadding = if (trailingIcon != null) 0.dp else horizontalPadding
            if (leadingIcon != null) {
                IconBox(leadingIcon, leadingIconColor, horizontalPadding)
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(
                        start = if (leadingIcon != null) 0.dp else horizontalPadding,
                        //end = trailingIconPadding
                    )
                    .weight(1f)
            ) {
                if (value.isEmpty() || value.isBlank()) {
                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        text = placeholder,
                        style = MaterialTheme.typography.body1,
                        color = AppColors.TextAndIcons.Tertiary,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )
                }
                innerTextField()
            }
            if (deleteNeeded) {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Sharp.Clear,
                        contentDescription = null,
                        tint = AppColors.TextAndIcons.Primary,
                        modifier = Modifier
                            .padding(start = horizontalPadding, end = trailingIconPadding)
                            .clickable {
                                setValue(value.replace(value, value.dropLast(value.length)))
                                onValueChange(value.replace(value, value.dropLast(value.length)))
                            }
                    )
                }
            }
            if (trailingIcon != null) {
                val imageVector = trailingIcon(value)
                if (imageVector != null) {
                    IconBox(
                        imageVector,
                        trailingIconColor,
                        horizontalPadding,
                        clickable = onClickTrailingIcon != null
                    ) {
                        onClickTrailingIcon?.invoke()
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        /*if (autofocus) {
            setValue(
                value.copy(
                    selection = TextRange(value.length)
                )
            )
            focusRequester.requestFocus()
        }*/
    }
    BackHandler(
        enabled = clearFocusOnBackPressed && isFocused
    ) {
        focusManager.clearFocus()
    }
}

@Composable
private fun IconBox(
    imageVector: ImageVector,
    iconColor: Color,
    horizontalPadding: Dp,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.BottomEnd
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(maxHeight / 2)
                .align(Alignment.Center)
                .clickable(enabled = clickable, onClick = onClick)
        )
    }
}

@Composable
fun FieldForDropDownMenu(
    initialValue: TextFieldValue = TextFieldValue(),
    borderShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    onFocus: ((Boolean) -> Unit)? = null,
    trailingIcon: @Composable ((String) -> ImageVector?)? = null,
    trailingIconColor: Color = AppColors.TextAndIcons.Primary,
    onClickTrailingIcon: (() -> Unit)? = null,
    placeholder: String = "",
    modifier: Modifier = Modifier,
) {
    var border by remember { mutableStateOf(Color.Transparent) }
    var isFocused by remember { mutableStateOf(false) }
    val (value, setValue) = remember(initialValue) { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier
            .height(48.dp)
            .onFocusChanged {
                isFocused = it.isFocused
                onFocus?.invoke(it.isFocused)
                border =
                    if (it.isFocused) AppColors.Brand.TextAndIcons.Default else Color.Transparent
            }
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = border
                ),
                shape = borderShape
            )
            .background(
                color = AppColors.GreyControlFill.Default,
                shape = borderShape
            )
            .then(modifier),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val horizontalPadding = 14.dp
        val trailingIconPadding = if (trailingIcon != null) 0.dp else horizontalPadding
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(
                    start = horizontalPadding,
                    //end = trailingIconPadding
                )
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = initialValue.text,
                style = MaterialTheme.typography.body1,
                color = AppColors.TextAndIcons.Primary,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
        }
        if (trailingIcon != null) {
            val imageVector = trailingIcon(value.text)
            if (imageVector != null) {
                IconBox(
                    imageVector,
                    trailingIconColor,
                    horizontalPadding,
                    clickable = onClickTrailingIcon != null
                ) {
                    onClickTrailingIcon?.invoke()
                }
            }
        }
    }
}