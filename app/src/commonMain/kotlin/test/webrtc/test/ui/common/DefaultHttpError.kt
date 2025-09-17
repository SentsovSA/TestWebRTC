package test.webrtc.test.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import test.webrtc.test.domain.entity.HttpRequestEntity
import test.webrtc.test.ui.theme.AppColors
import test.webrtc.test.ui.utils.toStringResource
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource

@Composable
fun DefaultHttpRequestError(
    httpRequestState: StateFlow<HttpRequestEntity>
) {
    val state by httpRequestState.collectAsState()
    if (state is HttpRequestEntity.Error) {
        val text = state.toStringResource ?: return
        DefaultHttpRequestError(stringResource(text))
    }
}

@Composable
fun DefaultHttpRequestError(text: String) {
    DefaultHttpRequestError {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.body2,
            color = AppColors.Critical.TextIcons.Default
        )
    }
}

@Composable
fun DefaultHttpRequestError(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        /*Icon(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(Res.drawable.error),
            tint = AppColors.Critical.TextIcons.Default,
            contentDescription = null
        )*/
        Spacer(modifier = Modifier.width(16.dp))
        content()
    }
}