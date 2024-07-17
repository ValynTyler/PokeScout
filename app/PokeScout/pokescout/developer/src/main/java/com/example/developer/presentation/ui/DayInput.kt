package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.input.text.NumberInput
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun DayInput(
    index: Int,
    state: DeveloperState,
    onChange: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    NumberInput(
        enabled = enabled,
        labelText = "D${index + 1}",
        value = state.inputData.dailyPoints[index]?.toString().orEmpty(),
        onChange = { onChange(index, it) },
        modifier = modifier.padding(8.dp),
    )
}