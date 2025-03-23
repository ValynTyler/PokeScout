package com.example.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.common.R

@Composable
fun PokemonImage(id: Int, modifier: Modifier = Modifier) {
    Image(
        bitmap = ImageBitmap.imageResource(id = R.drawable.pikachu),
        contentDescription = "pikachu",
        contentScale = ContentScale.FillHeight,
        filterQuality = FilterQuality.None,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonImagePreview() {
    PokemonImage(25, modifier = Modifier.fillMaxSize())
}