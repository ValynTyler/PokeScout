package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.components.text.TextInput
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun NameInput(
    state: DeveloperState,
    onChange: (String) -> Unit,
) {
    TextInput(
        labelText = "Trainer Name",
        value = state.inputData.trainer,
        enabled = !state.isWritingNfc,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onChange = onChange,
    )
}