package test.webrtc.test.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun rememberTextFieldValue(text: String = "") = remember { mutableStateOf(TextFieldValue(text)) }

@Composable
fun rememberTextFieldValue(text: AnnotatedString) = remember { mutableStateOf(TextFieldValue(text)) }