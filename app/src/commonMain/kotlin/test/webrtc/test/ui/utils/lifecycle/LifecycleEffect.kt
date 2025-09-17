package test.webrtc.test.ui.utils.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleEventEffect(
    event: Lifecycle.Event,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: () -> Unit
) {
    if (event == Lifecycle.Event.ON_DESTROY) {
        throw IllegalArgumentException("LifecycleEventEffect cannot be used to " +
                "listen for Lifecycle.Event.ON_DESTROY, since Compose disposes of the " +
                "composition before ON_DESTROY observers are invoked.")
    }

    // Safely update the current `onEvent` lambda when a new one is provided
    val currentOnEvent by rememberUpdatedState(onEvent)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, e ->
            if (e == event) {
                currentOnEvent()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun LifecycleStartEffect(
    key1: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleStartStopEffectScope.() -> LifecycleStopOrDisposeEffectResult
) {
    val lifecycleStartStopEffectScope = remember(key1, lifecycleOwner) {
        LifecycleStartStopEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleStartEffectImpl(lifecycleOwner, lifecycleStartStopEffectScope, effects)
}

@Composable
fun LifecycleStartEffect(
    key1: Any?,
    key2: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleStartStopEffectScope.() -> LifecycleStopOrDisposeEffectResult
) {
    val lifecycleStartStopEffectScope = remember(key1, key2, lifecycleOwner) {
        LifecycleStartStopEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleStartEffectImpl(lifecycleOwner, lifecycleStartStopEffectScope, effects)
}

@Composable
fun LifecycleStartEffect(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleStartStopEffectScope.() -> LifecycleStopOrDisposeEffectResult
) {
    val lifecycleStartStopEffectScope = remember(key1, key2, key3, lifecycleOwner) {
        LifecycleStartStopEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleStartEffectImpl(lifecycleOwner, lifecycleStartStopEffectScope, effects)
}

@Composable
fun LifecycleStartEffect(
    vararg keys: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleStartStopEffectScope.() -> LifecycleStopOrDisposeEffectResult
) {
    val lifecycleStartStopEffectScope = remember(*keys, lifecycleOwner) {
        LifecycleStartStopEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleStartEffectImpl(lifecycleOwner, lifecycleStartStopEffectScope, effects)
}

@Composable
private fun LifecycleStartEffectImpl(
    lifecycleOwner: LifecycleOwner,
    scope: LifecycleStartStopEffectScope,
    effects: LifecycleStartStopEffectScope.() -> LifecycleStopOrDisposeEffectResult
) {
    DisposableEffect(lifecycleOwner, scope) {
        var effectResult: LifecycleStopOrDisposeEffectResult? = null
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> with(scope) {
                    effectResult = effects()
                }

                Lifecycle.Event.ON_STOP -> effectResult?.runStopOrDisposeEffect()

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            effectResult?.runStopOrDisposeEffect()
        }
    }
}

interface LifecycleStopOrDisposeEffectResult {
    fun runStopOrDisposeEffect()
}

class LifecycleStartStopEffectScope(override val lifecycle: Lifecycle) : LifecycleOwner {
    inline fun onStopOrDispose(
        crossinline onStopOrDisposeEffect: LifecycleOwner.() -> Unit
    ): LifecycleStopOrDisposeEffectResult = object : LifecycleStopOrDisposeEffectResult {
        override fun runStopOrDisposeEffect() {
            onStopOrDisposeEffect()
        }
    }
}

@Composable
fun LifecycleResumeEffect(
    key1: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    val lifecycleResumePauseEffectScope = remember(key1, lifecycleOwner) {
        LifecycleResumePauseEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleResumeEffectImpl(lifecycleOwner, lifecycleResumePauseEffectScope, effects)
}

@Composable
fun LifecycleResumeEffect(
    key1: Any?,
    key2: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    val lifecycleResumePauseEffectScope = remember(key1, key2, lifecycleOwner) {
        LifecycleResumePauseEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleResumeEffectImpl(lifecycleOwner, lifecycleResumePauseEffectScope, effects)
}

@Composable
fun LifecycleResumeEffect(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    val lifecycleResumePauseEffectScope = remember(key1, key2, key3, lifecycleOwner) {
        LifecycleResumePauseEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleResumeEffectImpl(lifecycleOwner, lifecycleResumePauseEffectScope, effects)
}

@Composable
fun LifecycleResumeEffect(
    vararg keys: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    val lifecycleResumePauseEffectScope = remember(*keys, lifecycleOwner) {
        LifecycleResumePauseEffectScope(lifecycleOwner.lifecycle)
    }
    LifecycleResumeEffectImpl(lifecycleOwner, lifecycleResumePauseEffectScope, effects)
}

@Composable
private fun LifecycleResumeEffectImpl(
    lifecycleOwner: LifecycleOwner,
    scope: LifecycleResumePauseEffectScope,
    effects: LifecycleResumePauseEffectScope.() -> LifecyclePauseOrDisposeEffectResult
) {
    DisposableEffect(lifecycleOwner, scope) {
        var effectResult: LifecyclePauseOrDisposeEffectResult? = null
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> with(scope) {
                    effectResult = effects()
                }

                Lifecycle.Event.ON_PAUSE -> effectResult?.runPauseOrOnDisposeEffect()

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            effectResult?.runPauseOrOnDisposeEffect()
        }
    }
}

interface LifecyclePauseOrDisposeEffectResult {
    fun runPauseOrOnDisposeEffect()
}

class LifecycleResumePauseEffectScope(override val lifecycle: Lifecycle) : LifecycleOwner {
    inline fun onPauseOrDispose(
        crossinline onPauseOrDisposeEffect: LifecycleOwner.() -> Unit
    ): LifecyclePauseOrDisposeEffectResult = object : LifecyclePauseOrDisposeEffectResult {
        override fun runPauseOrOnDisposeEffect() {
            onPauseOrDisposeEffect()
        }
    }
}
