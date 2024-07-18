package com.example.trainer.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.trainer.R

@Composable
fun PokemonImage(
    id: Int,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        filterQuality = FilterQuality.None,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PokemonImagePreview(
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = ImageBitmap.imageResource(R.drawable.pokemon_pikachu),
        contentDescription = null,
        filterQuality = FilterQuality.None,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}