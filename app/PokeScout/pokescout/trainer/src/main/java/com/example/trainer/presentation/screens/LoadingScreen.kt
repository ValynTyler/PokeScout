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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getDrawable
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.trainer.R
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Preview
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = PokeBallDarkGrey)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center).size(128.dp),
//            bitmap = ImageBitmap.imageResource(id = R.drawable.loading),
            painter = rememberDrawablePainter(
                drawable = getDrawable(
                    LocalContext.current,
                    R.drawable.loading,
                )
            ),
            contentDescription = null,
//            filterQuality = FilterQuality.None,
            colorFilter = ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn)
        )
    }
}