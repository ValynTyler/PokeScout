package com.example.leader.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.screens.GymScreen
import com.example.leader.presentation.screens.InitScreen
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.leader.presentation.screens.LoadingScreen
import com.example.leader.presentation.screens.SelectScreen
import com.example.leader.presentation.screens.ValorScreen
import com.example.leader.presentation.ui.GreatballStripes
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.presentation.PokeballScaffold

@Preview
@Composable
fun MainView(
    state: LeaderState = LeaderState(),
    onInputEvent: (InputEvent) -> Unit = {},
) {
    PokeScoutTheme {
        PokeballScaffold(
            isClosed = state.isClosed,
            tophalfColor = GreatballBlue,
            bottomHalfColor = PokeballWhite,
            backgroundColor = PokeballGrey,
            uiColor = ThemeDarkGrey,
            onClicked = { onInputEvent(InputEvent.TogglePokeball) },
            pokeballDecoration = { GreatballStripes() }
        ) {
            when (state.activeScreenType) {
                LeaderScreenType.SelectScreen -> SelectScreen() {
                    onInputEvent(
                        InputEvent.SelectScreen(it)
                    )
                }
                LeaderScreenType.InitScreen -> InitScreen(state, onInputEvent)
                LeaderScreenType.GymScreen -> GymScreen(state, onInputEvent)
                LeaderScreenType.ValorScreen -> ValorScreen(state, onInputEvent)
                LeaderScreenType.LoadingScreen -> LoadingScreen()
            }
        }
    }
}