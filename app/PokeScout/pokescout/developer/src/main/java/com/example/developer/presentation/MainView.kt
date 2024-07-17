package com.example.developer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.scaffold.PokeScoutScaffold
import com.example.developer.presentation.input.InputEvent
import com.example.developer.presentation.ui.IdInput
import com.example.developer.presentation.ui.NameInput
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun MainView(
    state: DeveloperState,
    onInput: (InputEvent) -> Unit = {},
) {
    PokeScoutScaffold(
        onFabPress = {onInput(InputEvent.LockEvent)}
    ) {
        Column {
            NameInput(state) {onInput(InputEvent.TextEvent.ChangeTrainer(it))}
            IdInput(
                state,
                {onInput(InputEvent.TextEvent.ChangeSpecies(it))},
                {onInput(InputEvent.TextEvent.ChangeEvolutionChain(it))},
            )
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        state = DeveloperState()
    )
}