package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun DaysInputRow(
    state: DeveloperState,
    modifier: Modifier = Modifier,
    onChange: (Int, String) -> Unit = { _, _ -> },
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0..3) {
            DayInput(
                enabled = !state.isWritingNfc,
                state = state,
                index = i,
                onChange = onChange,
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun DaysInputRowPreview() {
    DaysInputRow(DeveloperState())
}