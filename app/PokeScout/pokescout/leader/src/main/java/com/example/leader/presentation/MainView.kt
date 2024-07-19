package com.example.leader.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.screens.InitScreen
import com.example.leader.presentation.ui.GreatballStripes
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.presentation.PokeballScaffold

@Composable
fun MainView(
    state: LeaderState,
    onInputEvent: (InputEvent) -> Unit = {},
) {
    PokeScoutTheme {
        PokeballScaffold(
            isClosed = state.isWritingNfc,
            tophalfColor = GreatballBlue,
            bottomHalfColor = PokeballWhite,
            PokeballGrey,
            ThemeDarkGrey,
            onClicked = { onInputEvent(InputEvent.ToggleNfcWriteMode) },
            pokeballDecoration = { GreatballStripes() }
        ) {
            if (!state.isWritingNfc) {
                InitScreen(
                    state,
                    onInputEvent,
                )
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        LeaderState(
            isWritingNfc = false
        )
    )
}