package test.webrtc.test.ui.common

import androidx.annotation.OptIn
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import test.webrtc.test.ui.theme.AppColors

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    url: String,
    isLongTap: Boolean,
    loading: () -> Unit,
    loaded: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    LaunchedEffect(isLongTap) {
        if (isLongTap) exoPlayer.pause()
        else exoPlayer.play()
    }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isLoading =
                    state == Player.STATE_BUFFERING
                if(!isLoading) loaded()

            }
        })
    }

    Box(modifier = modifier) {
        AndroidExternalSurface(
            modifier = Modifier.fillMaxSize(),
            onInit = {
                onSurface { surface, _, _ ->
                    exoPlayer.setVideoSurface(surface)
                    surface.onDestroyed { exoPlayer.setVideoSurface(null) }
                }
            }
        )

        if (isLoading) {
            loading()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF3587F2),
                                Color(0x383587F2)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.Brand.TextAndIcons.Default)
            }
        }
    }
}

