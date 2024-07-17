package com.example.trainer.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.trainer.R

@Composable
fun PokemonImage(
    id: Int
) {
    AsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        placeholder = painterResource(id = R.drawable.ic_launcher_background), // TODO
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        filterQuality = FilterQuality.None,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PokemonImagePreview() {
    PokemonImage(id = 25)
}