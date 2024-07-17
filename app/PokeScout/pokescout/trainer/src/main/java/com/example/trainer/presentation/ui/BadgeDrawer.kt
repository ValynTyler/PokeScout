package com.example.trainer.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.compose.theme.PokeScoutTheme
import com.example.trainer.R

@Composable
fun BadgeDrawer(
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet {
        Text(
            text = "Badges",
            modifier = Modifier
                .padding(16.dp),
        )
        Divider()
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            for (i in 0..<4) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    for (j in 0..<3) {
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.rainbow_badge),
                            contentDescription = null,
                            filterQuality = FilterQuality.None,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BadgeDrawerPreview() {
    PokeScoutTheme {
        BadgeDrawer()
    }
}