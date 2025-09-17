package test.webrtc.test.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.Foundation.NSRunLoop
import platform.Foundation.NSThread
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

private object MainRunDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) =
        NSRunLoop.mainRunLoop.performBlock(block::run)
}

private fun mainThread(block: () -> Unit) =
    if (NSThread.isMainThread()) block() else MainRunDispatcher.run { block() }

internal inline fun <T> runInMainThread(
    noinline block: (T) -> Unit
) : (T) -> Unit = { mainThread { block(it) } }

internal inline fun <T1, T2> runInMainThread(
    noinline block: (T1, T2) -> Unit
) : (T1, T2) -> Unit = { t1, t2 -> mainThread { block(t1, t2) } }