package com.example.trainer.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.evolution.ChainLink
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.pokemon.presentation.theme.pokeBallColors
import com.example.pokemon.presentation.ui.PokemonImage
import com.example.trainer.R
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.StatColumn
import com.example.trainer.presentation.ui.gym.grid.GymBadgeGrid
import com.example.trainer.presentation.ui.gym.list.GymBadgeList

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
                    var isGrid by remember { mutableStateOf(true) }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 60.dp, horizontal = 24.dp)
                            .padding(start = 24.dp)
                    ) {
                        if (isGrid) {
                            GymBadgeList(nfc = nfc)
                        } else {
                            GymBadgeGrid(nfc = nfc)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                            .padding(start = 24.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                        ) {
                            val iconSize = 32.dp
                            val interactionSource = remember { MutableInteractionSource() }
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.grid),
                                contentDescription = null,
                                filterQuality = FilterQuality.None,
                                colorFilter = if (!isGrid) ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn) else null,
                                modifier = Modifier
                                    .size(iconSize)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) { isGrid = true },
                            )
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.list),
                                contentDescription = null,
                                filterQuality = FilterQuality.None,
                                colorFilter = if (isGrid) ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn) else null,
                                modifier = Modifier
                                    .size(iconSize)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) { isGrid = false },
                            )
                        }
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