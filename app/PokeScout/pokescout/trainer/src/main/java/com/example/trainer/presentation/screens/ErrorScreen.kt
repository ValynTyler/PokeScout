package com.example.trainer.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.trainer.R

@Preview
@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = PokeBallDarkGrey)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center).size(128.dp),
            bitmap = ImageBitmap.imageResource(id = R.drawable.error),
            contentDescription = null,
            filterQuality = FilterQuality.None,
            colorFilter = ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn)
        )
    }
}