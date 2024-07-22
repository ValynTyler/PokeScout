package com.example.trainer.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.pokemon.presentation.PokeballScaffold
import com.example.trainer.presentation.screens.DisplayScreen
import com.example.trainer.presentation.screens.LoadingScreen
import com.example.trainer.presentation.state.Trainer

@Composable
fun MainView(
    state: Trainer.State,
    onClicked: () -> Unit,
) {
    PokeScoutTheme {
        PokeballScaffold(
                isClosed = state is Trainer.State.Closed,
                tophalfColor = PokeballRed,
                bottomHalfColor = PokeballWhite,
                backgroundColor = PokeballGrey,
                uiColor = ThemeDarkGrey,
                onClicked = onClicked,
            ) {
                when (state) {
                    Trainer.State.Closed -> {}
                    is Trainer.State.Open -> when (state.apiData) {
                        Trainer.ApiData.Error -> {}
                        Trainer.ApiData.Loading -> { LoadingScreen() }
                        is Trainer.ApiData.Success -> { DisplayScreen(state.apiData) }
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

