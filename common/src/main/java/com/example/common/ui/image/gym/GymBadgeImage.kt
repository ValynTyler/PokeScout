package com.example.common.ui.image.gym

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.common.R

@Composable
fun GymBadgeImage (
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.None,
) {
    val imageBitmap = ImageBitmap.imageResource(R.drawable.badge_earth)

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
private fun GymBadgeImagePreview() {
    GymBadgeImage(
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}
