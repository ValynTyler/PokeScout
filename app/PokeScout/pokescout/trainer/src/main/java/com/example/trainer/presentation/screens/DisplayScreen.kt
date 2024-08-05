package com.example.trainer.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import com.example.trainer.presentation.ui.StatColumn

@Composable
fun DisplayScreen(
    nfc: PokemonNfcData,
    api: Trainer.ApiData.Success
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize().padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 0.dp),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = PokeBallDarkGrey)
                        .padding(start = 24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 60.dp, horizontal = 16.dp)
                    ) {
                        repeat(4) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                repeat(3) {
                                    Column(
                                        modifier = Modifier.background(color = Color.Red)
                                    ) {
                                        Image(
                                            modifier = Modifier.size(60.dp),
                                            painter = painterResource(id = R.drawable.boulder),
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit,
                                        )
                                        Text("GYM", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp),
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