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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.gym.PokemonGym
import com.example.trainer.presentation.ui.gym.getGymBadgePainterInt

@Composable
fun GymBadgeListItem(gym: PokemonGym) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = getGymBadgePainterInt(gym)),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Text(text = gym.toString())
    }
}

@Preview
@Composable
fun GymBadgeListItemPreview() {
    GymBadgeListItem(gym = PokemonGym.NoPoison)
}