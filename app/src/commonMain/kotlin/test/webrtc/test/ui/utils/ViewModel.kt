package test.webrtc.test.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import test.webrtc.test.domain.ViewModelsModule
import test.webrtc.test.domain.base.ViewModel
import test.webrtc.test.ui.LocalViewModelsModuleProvider
import test.webrtc.test.ui.navigation.ViewModelScope
import test.webrtc.test.ui.navigation.ViewModelStore

@Composable
internal inline fun <reified T : ViewModel> rememberViewModel(
    scope: ViewModelScope,
    key: String? = null,
    crossinline factory: @DisallowComposableCalls ViewModelsModule.() -> T
): T {
    val viewModelsModule = LocalViewModelsModuleProvider.current
    val vm = remember(key) {
        ViewModelStore.get("${scope.name}-${T::class.simpleName}-$key") {
            factory(viewModelsModule)
        }
    }
    DisposableEffect(key) {
        vm.onStart()
        onDispose(vm::onDispose)
    }
    return vm
}

/*
@Composable
internal expect inline fun <reified VM : ViewModel> rememberViewModel(
    scope: ViewModelScope,
    key: String? = null,
    crossinline factory: @DisallowComposableCalls ViewModelsModule.() -> VM
) : VM

@Composable
internal inline fun <reified VM : ViewModel> rememberViewModel(
    key: String?,
    crossinline factory: @DisallowComposableCalls ViewModelsModule.() -> VM,
    block: @Composable (VM) -> VM
): VM {
    val viewModelsModule = LocalViewModelsModuleProvider.current
    val viewModel = block(factory(viewModelsModule))
    DisposableEffect(key) {
        viewModel.onStarted()
        onDispose(viewModel::onDispose)
    }
    return viewModel
}*/
