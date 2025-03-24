package com.example.common.ui.image

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun PokemonAnim(
    id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.None,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader
        .Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val imageId = context.resources.getIdentifier("number_${id}_anim", "drawable", context.packageName)

    Image(
        painter = rememberAsyncImagePainter(model = imageId, imageLoader = imageLoader, filterQuality = FilterQuality.None),
        contentDescription = "pikachu",
        contentScale = contentScale,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonAnimPreview() {
    PokemonAnim(
        id = 130,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}