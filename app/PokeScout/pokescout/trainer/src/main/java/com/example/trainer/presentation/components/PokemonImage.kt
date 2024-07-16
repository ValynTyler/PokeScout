package com.example.trainer.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.pokemon.domain.PokemonNfcData
import com.example.trainer.R
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun PokemonImage(
    state: TrainerState
) {
    state.nfcData?.let { data ->
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${data.speciesId}.png",
            placeholder = painterResource(id = R.drawable.ic_launcher_background), // TEMP
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            filterQuality = FilterQuality.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PokemonImagePreview() {
    PokemonImage(
        TrainerState(
            nfcData = PokemonNfcData()
        )
    )
}