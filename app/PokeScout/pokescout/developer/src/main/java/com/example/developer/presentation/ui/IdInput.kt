package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.developer.presentation.components.text.NumberInput
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun IdInput(
    state: DeveloperState,
    onChangeSpecies: (String) -> Unit,
    onChangeEvolution: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        NumberInput(
            labelText = "Species ID",
            value = state.inputData.species?.toString().orEmpty(),
            enabled = !state.isWritingNfc,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            onChange = onChangeSpecies,
        )
        NumberInput(
            labelText = "Evolution ID",
            value = state.inputData.species?.toString().orEmpty(),
            enabled = !state.isWritingNfc,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            onChange = onChangeEvolution,
        )
    }
}