package com.example.common.ui.image

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
fun PlaceholderImage (
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.None,
) {
    val imageBitmap = ImageBitmap.imageResource(R.drawable.placeholder)
    Image(
        bitmap = imageBitmap,
        filterQuality = FilterQuality.None,
        contentDescription = "placeholder",
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonPlaceholderPreview  () {
    PlaceholderImage(
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}