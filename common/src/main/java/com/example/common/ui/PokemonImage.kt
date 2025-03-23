package com.example.common.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.common.R

@Composable
fun PokemonImage(id: Int, modifier: Modifier = Modifier) {
    val imageLoader = ImageLoader
        .Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberImagePainter(data = R.drawable.pikachu_anim, imageLoader = imageLoader),
        contentDescription = "pikachu",
        contentScale = ContentScale.Fit,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonImagePreview() {
    PokemonImage(25, modifier = Modifier.fillMaxSize())
}