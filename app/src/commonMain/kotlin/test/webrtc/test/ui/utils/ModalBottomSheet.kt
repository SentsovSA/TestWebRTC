package test.webrtc.test.ui.utils

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow

typealias ModalBottomSheetContent = @Composable ColumnScope.() -> Unit

class ModalBottomSheet(
    val state: ModalBottomSheetState
) {

    val isVisible get() = state.isVisible
    val content = MutableStateFlow<ModalBottomSheetContent>{}

    suspend fun show(content: ModalBottomSheetContent) {
        this.content.value = content
        state.show()
    }

    suspend fun hide() = state.hide()

}