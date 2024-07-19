package com.example.trainer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.ThemeDarkGrey
import com.example.pokemon.presentation.PokemonImage
import com.example.trainer.presentation.ui.StatBox
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun InfoScreen(
    state: TrainerState,
) {
    Surface {
        Column {
            PokemonImage(
                state.nfcData?.speciesId ?: 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            )
            Spacer(modifier = Modifier.height(8.dp))
            val targetXp = when (state.currentEvolutionStage()) {
                1 -> {
                    if (state.evolutionData != null) {
                        when (state.evolutionData.maxLength()) {
                            2 -> 600
                            3 -> 400
                            else -> 0
                        }
                    } else {
                        0
                    }
                }

                2 -> {
                    if (state.evolutionData != null && state.evolutionData.maxLength() == 3) {
                        600
                    } else {
                        0
                    }
                }

                else -> 0
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                progress = state.nfcData?.xp()?.div(targetXp.toFloat()) ?: 0f
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatBox(
                state,
                modifier = Modifier
                    .background(color = ThemeDarkGrey)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen(state = TrainerState())
}