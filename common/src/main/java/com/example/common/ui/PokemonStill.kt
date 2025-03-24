package com.example.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonStill (
    id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.None,
) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier("number_${id}", "drawable", context.packageName)
    val imageBitmap = ImageBitmap.imageResource(imageId)
    Image(
        bitmap = imageBitmap,
        filterQuality = FilterQuality.None,
        contentDescription = "pikachu",
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonStillPreview() {
    PokemonStill(
        id = 130,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}