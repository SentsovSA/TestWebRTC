package test.webrtc.test.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import test.webrtc.test.ui.navigation.LocalRouterProvider
import test.webrtc.test.ui.navigation.BackHandler

@Composable
fun UnimplementedScreen(text: String) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
    val nav = LocalRouterProvider.current
    BackHandler(
        enabled = nav.canPop
    ) {
        nav.pop()
    }
}