package test.webrtc.test.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

abstract class ViewModel {

    private var scope: CoroutineScope? = null

    protected val viewModelScope get() : CoroutineScope {
        val current = scope
        if (current != null && current.isActive) return current
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        return scope!!
    }

    init {
        println("ViewModel [CREATED] ${this::class.simpleName}")
    }

    open fun onStart() = Unit
    open fun onDispose() = Unit

    open fun cancel() {
        viewModelScope.cancel()
        println("ViewModel [CANCEL] ${this::class.simpleName}")
    }

}