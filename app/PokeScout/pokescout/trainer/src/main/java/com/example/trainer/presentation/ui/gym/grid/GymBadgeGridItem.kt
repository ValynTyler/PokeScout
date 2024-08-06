package com.example.trainer.presentation.ui.gym.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.domain.model.gym.PokemonGym
import com.example.trainer.presentation.ui.gym.getGymBadgePainterInt

@Composable
fun GymBadgeGridItem(gym: PokemonGym, isBeaten: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imageBitmap = ImageBitmap.imageResource(getGymBadgePainterInt(gym))
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = imageBitmap,
            filterQuality = FilterQuality.None,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = if (isBeaten) null else ColorFilter.tint(Color.Black, BlendMode.SrcIn)
        )
    }
}

@Preview
@Composable
fun GymBadgeGridItemPreview() {
    GymBadgeGridItem(gym = PokemonGym.NoPoison, true)
}