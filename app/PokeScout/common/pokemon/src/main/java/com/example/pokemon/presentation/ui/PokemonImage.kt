package com.example.pokemon.presentation.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

fun urlById(speciesId: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$speciesId.png"
//    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/$speciesId.gif"
}

@Composable
fun PokemonImage(id: Int, modifier: Modifier = Modifier) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model = urlById(id),
        imageLoader = imageLoader,
        contentDescription = null,
        filterQuality = FilterQuality.None,
        contentScale = ContentScale.FillWidth,
        modifier = modifier.fillMaxWidth()
    )
}