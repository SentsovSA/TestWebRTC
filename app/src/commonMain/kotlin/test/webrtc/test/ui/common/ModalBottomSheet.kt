package test.webrtc.test.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModalBottomSheet(
    sheetState: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    /*androidx.compose.material3.ModalBottomSheet(
        onDismissRequest = {

        },
        modifier = modifier,
        sheetState = sheetState,
        content = sheetContent
    )*/
    var sheetModifier = Modifier.imePadding().then(modifier)
    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == ModalBottomSheetValue.Expanded) {
            sheetModifier = sheetModifier.navigationBarsPadding()
        }
    }

    ModalBottomSheetLayout(
        modifier = sheetModifier,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        sheetElevation = 0.dp,
        sheetContent = sheetContent,
        content = content
    )
}