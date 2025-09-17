package test.webrtc.test.ui.utils.lifecycle

import androidx.annotation.CheckResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.LifecycleOwner

@CheckResult
@Composable
fun dropUnlessStarted(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: () -> Unit,
): () -> Unit = dropUnlessStateIsAtLeast(State.STARTED, lifecycleOwner, block)

@CheckResult
@Composable
fun dropUnlessResumed(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: () -> Unit,
): () -> Unit = dropUnlessStateIsAtLeast(State.RESUMED, lifecycleOwner, block)

@CheckResult
@Composable
private fun dropUnlessStateIsAtLeast(
    state: State,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: () -> Unit,
): () -> Unit {
    require(state != State.DESTROYED) {
        "Target state is not allowed to be `Lifecycle.State.DESTROYED` because Compose disposes " +
                "of the composition before `Lifecycle.Event.ON_DESTROY` observers are invoked."
    }
    return {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(state)) block()
    }
}