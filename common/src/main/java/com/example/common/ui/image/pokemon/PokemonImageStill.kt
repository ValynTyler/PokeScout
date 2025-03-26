package com.example.common.ui.image.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonImageStill (
    id: Int,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
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
        colorFilter = colorFilter,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonStillPreview() {
    PokemonImageStill(
        id = 130,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}