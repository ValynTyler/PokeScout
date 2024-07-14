package com.example.pokescoutdeveloper.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokescoutdeveloper.presentation.DeveloperState
import com.example.pokescoutdeveloper.presentation.events.TextEvent

@Composable
fun MainView(
    state: DeveloperState,
    onTextChange: (TextEvent) -> Unit = {},
) {

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus() }
    ) {
        TextInput("Trainer Name", state.inputName) { onTextChange(TextEvent.ChangedName(it)) }
        NumberInput("ID", state.inputId?.toString().orEmpty()) { onTextChange(TextEvent.ChangedId(it)) }
        NumberInput("XP", state.inputXp?.toString().orEmpty()) { onTextChange(TextEvent.ChangedXp(it)) }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        state = DeveloperState(
            inputXp = 69,
            inputId = 42,
            inputName = "Jorbo"
        )
    )
}