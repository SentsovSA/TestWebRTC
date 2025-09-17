package test.webrtc.test.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.webrtc.test.ui.images.AppImages
import test.webrtc.test.ui.images.SmallArrowDown
import test.webrtc.test.ui.theme.AppColors
import io.github.alexzhirkevich.cupertino.CupertinoDropdownMenu
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AppDropDownField(
    label: String,
    options: Map<String, String>,
    selectedValue: MutableState<String>,
    onItemSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionKey by remember {
        mutableStateOf(
            options.toList()
                .firstOrNull { option -> option.first == selectedValue.value }
                ?.first
                ?: throw RuntimeException("Список опций не содержит ключ: $selectedValue")
        )
    }

    LaunchedEffect(selectedValue.value) {
        selectedOptionKey = options.toList()
            .firstOrNull { option -> option.first == selectedValue.value }
            ?.first
            ?: throw RuntimeException("Список опций не содержит ключ: $selectedValue")
    }
    var width by remember { mutableStateOf(0.dp) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(16.dp).onSizeChanged {
            width = it.width.dp
        }
    ) {
        FieldForDropDownMenu(
            initialValue = TextFieldValue(options[selectedOptionKey]!!),
            trailingIcon = {
                AppImages.SmallArrowDown
            },
            modifier = Modifier.onSizeChanged {
                width = it.width.dp
            },
        )
        CupertinoDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            width = width,
            paddingValues = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .width(width)
                .border(
                    border = BorderStroke(
                        1.dp, AppColors.Brand.TextAndIcons.Default
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    AppColors.Background.Primary,
                    RoundedCornerShape(8.dp)
                )

        ) {
            options.entries.sortedBy { it.key.toInt() }.forEach { entry ->
                CustomRippleEffectButton(
                    modifier = Modifier.width(width),
                    text = entry.value,
                    onClick = {
                        selectedOptionKey = entry.key
                        onItemSelect(selectedOptionKey)
                        expanded = false
                    },
                    isFirst = entry == options.entries.first(),
                    isLast = entry == options.entries.last()
                )
            }
        }
    }
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AppDropDownFilter(
    label: String,
    options: Map<String, String>,
    selectedValue: MutableState<String>,
    onItemSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionKey by remember {
        mutableStateOf(
            options.toList()
                .firstOrNull { option -> option.first == selectedValue.value }
                ?.first
                ?: throw RuntimeException("Список опций не содержит ключ: $selectedValue")
        )
    }

    LaunchedEffect(selectedValue.value) {
        selectedOptionKey = options.toList()
            .firstOrNull { option -> option.first == selectedValue.value }
            ?.first
            ?: throw RuntimeException("Список опций не содержит ключ: $selectedValue")
    }
    var width by remember { mutableStateOf(0.dp) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.onSizeChanged {
            width = it.width.dp
        }
    ) {
       /* FilterButton(
            text = options[selectedOptionKey]!!,
            isOpened = expanded,
            isIconNeeded = true
        ) {

        }*/
        CupertinoDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(width)
                .border(
                    border = BorderStroke(
                        1.dp, AppColors.Brand.TextAndIcons.Default
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    AppColors.Background.Primary,
                    RoundedCornerShape(8.dp)
                )

        ) {
            val sortedEntries = options.entries
                .sortedWith(
                    compareBy(
                        { it.key.toIntOrNull() == null },
                    { it.key.toIntOrNull() ?: Int.MAX_VALUE },
                    { it.key }
                ))
            sortedEntries
                .forEach { entry ->
                    CustomRippleEffectButton(
                        modifier = Modifier.width(width),
                        text = entry.value,
                        onClick = {
                            selectedOptionKey = entry.key
                            onItemSelect(selectedOptionKey)
                            expanded = false
                        },
                        isFirst = entry == options.entries.first(),
                        isLast = entry == options.entries.last()
                    )
                }
        }
    }
}

/**
 * Данные кнопки надо использовать во всех диалоговых окнах,
 * так как со стандартным риплом на айос происходят подергивания
 * */
@Composable
fun CustomRippleEffectButton(
    modifier: Modifier = Modifier,
    color: Color = AppColors.Background.Primary,
    textColor: Color = AppColors.TextAndIcons.Primary,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    shape: Shape = RectangleShape
) {

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val rippleScale = remember { Animatable(0f) }
    val alpha by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) 0.15f else 0f,
        animationSpec = tween(200)
    )
    val textAlpha by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) 0.7f else 1f,
        animationSpec = tween(200)
    )
    val coroutineScope = rememberCoroutineScope()

    val customShape = if (isFirst) RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    else if (isLast) RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
    else shape

    Box(
        modifier = modifier
            .height(48.dp)
            .noRippleClickable {
                onClick()
            }
            .background(color, customShape)
            .pointerInput(buttonState) {
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Pressed) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        coroutineScope.launch {
                            rippleScale.snapTo(0f)
                            launch {
                                rippleScale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(durationMillis = 300)
                                )
                            }
                        }
                        ButtonState.Pressed
                    }

                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(
                    scaleX = rippleScale.value,
                    scaleY = rippleScale.value,
                    alpha = alpha
                )
                .background(color = Color.Black, customShape)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .alpha(textAlpha),
            text = text,
            textAlign = textAlign,
            color = textColor
        )
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

private enum class ButtonState { Pressed, Idle }
