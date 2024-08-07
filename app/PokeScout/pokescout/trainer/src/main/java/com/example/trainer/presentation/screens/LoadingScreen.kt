package com.example.trainer.presentation.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.trainer.R

@Preview
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = PokeBallDarkGrey)
    ) {

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.loading)
                .build(),
            imageLoader = imageLoader,
            contentDescription = null,
            filterQuality = FilterQuality.None,
            colorFilter = ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn),
            modifier = Modifier.align(Alignment.Center).size(128.dp),
        )
    }
}