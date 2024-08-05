package com.example.trainer.presentation.ui.gym.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.gym.PokemonGym
import com.example.trainer.R

@Composable
fun GymBadgeGridItem(gym: PokemonGym) {
    Column {
        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.boulder),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Text(text = gym.toString())
    }
}

@Preview
@Composable
fun GymBadgeGridItemPreview() {
    GymBadgeGridItem(gym = PokemonGym.NoPoison)
}