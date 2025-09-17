package test.webrtc.test.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable

private val savedIntState = mutableMapOf<String, Int>()
private val savedTopAppBarState = mutableMapOf<String, TopAppBarState>()
private val savedLazyListState = mutableMapOf<String, LazyListState>()

@Composable
fun rememberStaticIntState(
    key: String,
    initialValue: Int = 0
): MutableIntState {
    val state = rememberSaveable(key) {
        mutableIntStateOf( savedIntState[key] ?: initialValue)
    }
    DisposableEffect(Unit) {
        onDispose {
            savedIntState[key] = state.value
        }
    }
    return state
}

@Composable
fun rememberStaticTopAppBarState(
    key: String,
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f
): TopAppBarState {
    val state = rememberSaveable(key, saver = TopAppBarState.Saver) {
        val saved = savedTopAppBarState[key]
        TopAppBarState(
            initialHeightOffsetLimit = saved?.heightOffsetLimit ?: initialHeightOffsetLimit,
            initialHeightOffset = saved?.heightOffset ?: initialHeightOffset,
            initialContentOffset = saved?.contentOffset ?: initialContentOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            savedTopAppBarState[key] = state
        }
    }
    return state
}

@Composable
fun rememberStaticLazyListState(
    key: String,
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val state = rememberSaveable(key, saver = LazyListState.Saver) {
        val saved = savedLazyListState[key]
        LazyListState(
            firstVisibleItemIndex = saved?.firstVisibleItemIndex ?: initialFirstVisibleItemIndex,
            firstVisibleItemScrollOffset = saved?.firstVisibleItemScrollOffset ?: initialFirstVisibleItemScrollOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            savedLazyListState[key] = state
        }
    }
    return state
}