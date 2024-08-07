package com.example.trainer.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.presentation.theme.PokeScoutTheme
import com.example.pokemon.presentation.theme.pokeBallColors
import com.example.pokemon.presentation.ui.PokeballScaffold
import com.example.trainer.presentation.screens.display.DisplayScreen
import com.example.trainer.presentation.screens.ErrorScreen
import com.example.trainer.presentation.screens.LoadingScreen
import com.example.trainer.presentation.state.Trainer

@Composable
fun MainView(
    state: Trainer.State,
    onEvent: (Trainer.Event) -> Unit,
) {
    PokeScoutTheme {
        PokeballScaffold(
            isClosed = state is Trainer.State.Closed,
            pokeBallColors = pokeBallColors,
            onClicked = { onEvent(Trainer.Event.Button.LatchPressed) },
        ) {
            when (state) {
                Trainer.State.Closed -> {}
                is Trainer.State.Open -> when (state.apiData) {
                    Trainer.ApiData.Loading -> {
                        LoadingScreen()
                    }

                    is Trainer.ApiData.Success -> {
                        DisplayScreen(
                            state.nfcData,
                            state.apiData,
                            state.drawerState,
                            state.badgeViewMode,
                        ) { onEvent(it) }
                    }

                    Trainer.ApiData.Error -> {
                        ErrorScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        Trainer.State.Closed
    ) {}
}

