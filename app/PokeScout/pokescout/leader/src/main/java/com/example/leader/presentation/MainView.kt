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
import com.example.leader.presentation.ui.PokeballScaffold
import com.example.leader.presentation.viewmodel.LeaderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    state: LeaderState,
    onInputEvent: (InputEvent) -> Unit = {},
) {
    PokeScoutTheme {
        PokeballScaffold(
            GreatballBlue,
            PokeballWhite,
            PokeballGrey,
            ThemeDarkGrey,
            state = state,
            onInputEvent = onInputEvent
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