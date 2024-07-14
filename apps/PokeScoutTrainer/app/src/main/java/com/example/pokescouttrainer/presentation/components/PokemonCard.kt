package com.example.pokescouttrainer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokescouttrainer.R
import com.example.pokescouttrainer.domain.nfc.PokemonNfcData
import com.example.pokescouttrainer.domain.species.Language
import com.example.pokescouttrainer.presentation.TrainerState

@Composable
fun PokemonCard(
    state: TrainerState,
    modifier: Modifier = Modifier,
) {
    state.nfcData?.let { data ->
        Box(
            modifier = modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${data.speciesId}.png",
                    placeholder = painterResource(id = R.drawable.ic_launcher_background), // TEMP
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    filterQuality = FilterQuality.None,
                    modifier = Modifier.fillMaxWidth()
                )
                LinearProgressIndicator(
                    progress = 0.3f,
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier)
                    state.speciesData?.let { species ->
                        StatField("ID", species.id.toString())
                        StatField("Species", species.name())
                    }
                    StatField("XP", data.pokemonXp.toString())
                    StatField("Trainer", data.trainerName)
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(
        state = TrainerState(
            nfcData = PokemonNfcData(
                pokemonXp = 69420,
                trainerName = "Dan Rodgerson Esq. the Second"
            )
        )
    )
}
