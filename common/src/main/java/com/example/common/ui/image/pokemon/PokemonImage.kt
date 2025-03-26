package com.example.common.ui.image.pokemon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonImage(
    id: Int,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.None,
) {
    if (id < 650) {
        PokemonImageAnim(id, modifier, colorFilter, contentScale)
    } else {
        PokemonImageStill(id, modifier, colorFilter, contentScale)
    }
}

@Preview
@Composable
private fun PokemonImagePreview() {
    PokemonImage(
        650,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxSize(),
    )
}