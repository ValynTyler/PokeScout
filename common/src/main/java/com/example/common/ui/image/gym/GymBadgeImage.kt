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
import com.example.common.model.GymType

@Composable
fun GymBadgeImage (
    gymType: GymType,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.None,
) {
    val imageBitmap  = ImageBitmap.imageResource(when (gymType) {
        GymType.Endurance       -> R.drawable.badge_boulder
        GymType.Domestic        -> R.drawable.badge_hive
        GymType.Acquaintance    -> R.drawable.badge_soul
        GymType.Parkour         -> R.drawable.badge_earth
        GymType.Movie           -> R.drawable.badge_fog
        GymType.Fleecy          -> R.drawable.badge_cascade
        GymType.Flora           -> R.drawable.badge_rainbow
        GymType.Safe            -> R.drawable.badge_rising
        GymType.NoPoison        -> R.drawable.badge_mineral
        GymType.NonViolent      -> R.drawable.badge_storm
        GymType.BuildIt         -> R.drawable.badge_plain
        GymType.PokeCulinaria   -> R.drawable.badge_volcano
        GymType.Stargazer       -> R.drawable.badge_thunder
        GymType.Quest           -> R.drawable.badge_marsh
        GymType.PokeChef        -> R.drawable.badge_zephyr
    })

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
        GymType.Stargazer,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}