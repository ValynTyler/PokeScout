package com.example.trainer.presentation.ui.gym.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.gym.PokemonGym
import com.example.trainer.presentation.ui.gym.getGymBadgePainterInt

@Composable
fun GymBadgeListItem(gym: PokemonGym, isBeaten: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        val imageBitmap = ImageBitmap.imageResource(getGymBadgePainterInt(gym))
        Image(
            modifier = Modifier.size(48.dp),
            bitmap = imageBitmap,
            filterQuality = FilterQuality.None,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = if (isBeaten) null else ColorFilter.tint(Color.Black, BlendMode.SrcIn)
        )
        Text(text = if (isBeaten) "$gym GYM" else "???")
    }
}

@Preview
@Composable
fun GymBadgeListItemPreview() {
    GymBadgeListItem(gym = PokemonGym.PokeCulinaria, isBeaten = true)
}