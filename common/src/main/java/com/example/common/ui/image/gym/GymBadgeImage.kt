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
import com.example.common.model.Gym

@Composable
fun GymBadgeImage (
    gym: Gym,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.None,
) {
    val imageBitmap  = ImageBitmap.imageResource(when (gym) {
        Gym.Endurance       -> R.drawable.badge_boulder
        Gym.Domestic        -> R.drawable.badge_hive
        Gym.Acquaintance    -> R.drawable.badge_soul
        Gym.Parkour         -> R.drawable.badge_earth
        Gym.Movie           -> R.drawable.badge_fog
        Gym.Fleecy          -> R.drawable.badge_cascade
        Gym.Flora           -> R.drawable.badge_rainbow
        Gym.Safe            -> R.drawable.badge_rising
        Gym.NoPoison        -> R.drawable.badge_mineral
        Gym.NonViolent      -> R.drawable.badge_storm
        Gym.BuildIt         -> R.drawable.badge_plain
        Gym.PokeCulinaria   -> R.drawable.badge_volcano
        Gym.Stargazer       -> R.drawable.badge_thunder
        Gym.Quest           -> R.drawable.badge_marsh
        Gym.PokeChef        -> R.drawable.badge_zephyr
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
        Gym.Stargazer,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
    )
}