package com.example.pokescouttrainer.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokescouttrainer.R
import com.example.pokescouttrainer.domain.model.TagData
import com.example.pokescouttrainer.presentation.TrainerState

@Composable
fun PokemonCard(
    state: TrainerState,
    modifier: Modifier = Modifier,
) {
    state.tagData?.let { data ->
        Box(
            modifier = modifier.padding(16.dp)
        ) {
            Column {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${data.speciesId}.png",
                    placeholder = painterResource(id = R.drawable.ic_launcher_background), // temp
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    filterQuality = FilterQuality.None,
                    modifier = Modifier.fillMaxWidth()
                )
                LinearProgressIndicator(
                    progress = 0.3f,
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(state = TrainerState(tagData = TagData()))
}
