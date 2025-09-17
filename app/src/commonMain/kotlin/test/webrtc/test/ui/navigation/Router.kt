package test.webrtc.test.ui.navigation

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal val LocalRouterProvider = compositionLocalOf<Router> {
    error("Router not provided")
}

class Router(
    initialPath: String = "/",
    initialExtra: Any? = null,
    private val onPop: (Route) -> Unit = {}
) {

    private val stack = mutableListOf(Route(initialPath, initialExtra))

    private val _currentRoute = MutableStateFlow(stack.last())
    val currentRoute = _currentRoute.asStateFlow()

    val canPop get() = stack.size > 1

    val prevRoute get() = stack[stack.lastIndex - 1]

    var swipeBackCallback: (() -> Unit)? = null

    fun onSwipeBack() {
        swipeBackCallback?.invoke()
    }

    fun push(path: String, arguments: Any? = null) {
        stack.add(Route(path, arguments))
        _currentRoute.value = stack.last()
    }

    fun replace(path: String, arguments: Any? = null) {
        onPop(stack.removeLast())
        stack.add(Route(path, arguments))
        _currentRoute.value = stack.last()
    }

    fun replaceAndSetPrevious(path: String, arguments: Any? = null, prevRoute: String, prevRouteArguments: Any? = null) {
        onPop(stack.removeAt(stack.size - 1))
        stack.add(Route(prevRoute, prevRouteArguments))
        stack.add(Route(path, arguments))
        _currentRoute.value = stack.last()
    }

    fun replaceAll(path: String, arguments: Any? = null) {
        stack.clear()
        stack.add(Route(path, arguments))
        _currentRoute.value = stack.last()
    }

    fun pop(arguments: Any? = null) {
        onPop(stack.removeAt(stack.size - 1))
        _currentRoute.value = arguments?.let {
            stack.last().copy(arguments = it)
        } ?: stack.last()
    }

}

data class Route(
    val path: String,
    val arguments: Any?
)