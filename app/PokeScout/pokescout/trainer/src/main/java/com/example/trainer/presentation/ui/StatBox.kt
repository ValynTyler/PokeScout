package com.example.trainer.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.display.StatField
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun StatBox(
    state: TrainerState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.padding(8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            StatField(field = "Trainer", entry = state.nfcData?.trainerName.orEmpty())
            StatField(field = "ID", entry = state.nfcData?.speciesId?.toString().orEmpty())
            StatField(field = "XP", entry = state.nfcData?.xp()?.toString().orEmpty())
            val stage = state.totalEvolutionStages?.let {
                state.currentEvolutionStage?.let {
                    "${state.currentEvolutionStage}/${state.totalEvolutionStages}"
                }
            }.orEmpty()
            StatField(field = "Stage", entry = state.currentEvolutionStage.toString())
        }
    }
}

@Preview
@Composable
fun StatBoxPreview() {
    StatBox(TrainerState())
}