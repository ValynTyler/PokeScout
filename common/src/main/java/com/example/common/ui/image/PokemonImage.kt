package com.example.common.ui.image

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonImage(
    id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.None,
) {
    if (id < 650) {
        PokemonImageAnim(id, modifier, contentScale)
    } else {
        PokemonImageStill(id, modifier, contentScale)
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