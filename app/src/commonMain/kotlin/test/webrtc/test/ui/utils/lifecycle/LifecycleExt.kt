package test.webrtc.test.ui.utils.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle

@Composable
fun Lifecycle.currentStateAsState(): State<Lifecycle.State> = currentStateFlow.collectAsState()