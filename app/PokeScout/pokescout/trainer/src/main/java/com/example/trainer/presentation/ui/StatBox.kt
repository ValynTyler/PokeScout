package com.example.trainer.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun StatBox(
//    state: TrainerState,
    modifier: Modifier = Modifier,
) {
//    Column(
//        verticalArrangement = Arrangement.Top,
//        modifier = modifier.padding(8.dp),
//    ) {
//        Column(
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.fillMaxSize()
//        ) {
//            StatField(field = "Trainer", entry = state.nfcData?.trainerName.orEmpty())
//            StatField(field = "Pokemon", entry = state.nfcData?.trainerName.orEmpty())
//            StatField(field = "XP", entry = state.nfcData?.xp()?.toString().orEmpty())
//            val totalEvolutionStages = state.evolutionData?.maxLength()
//            val currentEvolutionStage = state.currentEvolutionStage()
//            val stage = totalEvolutionStages?.let {
//                currentEvolutionStage?.let {
//                    "${currentEvolutionStage}/${totalEvolutionStages}"
//                }
//            }.orEmpty()
//            StatField(field = "Stage", entry = stage)
//        }
//    }
}

@Preview
@Composable
fun StatBoxPreview() {
//    StatBox(TrainerState())
}