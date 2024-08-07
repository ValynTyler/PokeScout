package com.example.trainer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.evolution.ChainLink
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.pokeBallColors
import com.example.pokemon.presentation.ui.PokemonImage
import com.example.trainer.R
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.MenuButton
import com.example.trainer.presentation.ui.StatColumn
import com.example.trainer.presentation.ui.gym.GymBadgeDisplay

@Composable
fun DisplayScreen(
    nfc: PokemonNfcData,
    api: Trainer.ApiData.Success
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PokeBallDarkGrey)
                ) {
                    var isBadges by remember { mutableStateOf(true) }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                            .padding(start = 24.dp)
                    ) {
                        MenuButton(
                            imageResourceId = if (isBadges) R.drawable.badge else R.drawable.clock,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.BottomEnd),
                        ) { isBadges = !isBadges }
                    }
                    if (isBadges) {
                        GymBadgeDisplay(nfc = nfc)
                    } else {

                    }
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            PokemonImage(
                id = api.species.id,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
                progress = nfc.xpPercent(
                    api.evolution.stage(api.species.id).getOrDefault(0),
                    api.evolution.length()
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatColumn(
                nfc, api,
                Modifier
                    .background(pokeBallColors.emptyColor)
            )
        }
    }
}

@Preview
@Composable
fun DisplayScreenPreview() {
    DisplayScreen(
        nfc = PokemonNfcData(),
        api = Trainer.ApiData.Success(
            species = PokemonSpecies(25, "pickake", emptyMap(), null, 10),
            evolution = EvolutionChain(
                10,
                ChainLink(PokemonSpecies(172, "pigu", emptyMap(), null, 10), emptyList())
            )
        )
    )
}