package com.example.trainer.presentation

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.PokeballScaffold
import com.example.trainer.presentation.screens.ReadScreen
import com.example.trainer.presentation.ui.BadgeDrawer
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun MainView(
    state: TrainerState,
    onClicked: () -> Unit = {},
) {
    PokeScoutTheme {
        ModalNavigationDrawer(
            drawerContent = { BadgeDrawer(state) }
        ) {
            PokeballScaffold(
                isClosed = state.isOpen,
                tophalfColor = PokeballRed,
                bottomHalfColor = PokeballWhite,
                PokeballGrey,
                ThemeDarkGrey,
                onClicked = onClicked,
            ) {
                ReadScreen(state)
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        TrainerState(
            nfcData = PokemonNfcData(),
        )
    )
}

