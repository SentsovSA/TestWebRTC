package test.webrtc.test.ui.common

import androidx.compose.runtime.Composable

@Composable
expect fun RedirectToEmail(recipient: String, subject: String, body: String, isError: (Boolean) -> Unit)