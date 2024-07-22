package com.example.trainer.presentation.screens
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//
//@Composable
//fun InfoScreen(
//    state: TrainerState,
//) {
//    Surface {
//        Column {
//            PokemonImage(
//                state.nfcData?.speciesId ?: 0,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            val targetXp = when (state.currentEvolutionStage()) {
//                1 -> {
//                    if (state.evolutionData != null) {
//                        when (state.evolutionData.maxLength()) {
//                            2 -> 600
//                            3 -> 400
//                            else -> 0
//                        }
//                    } else {
//                        0
//                    }
//                }
//
//                2 -> {
//                    if (state.evolutionData != null && state.evolutionData.maxLength() == 3) {
//                        600
//                    } else {
//                        0
//                    }
//                }
//
//                else -> 0
//            }
//            LinearProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(8.dp),
//                progress = state.nfcData?.xp()?.div(targetXp.toFloat()) ?: 0f
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            StatBox(
//                state,
//                modifier = Modifier
//                    .background(color = ThemeDarkGrey)
//                    .fillMaxSize()
//            )
//        }
//    }
//}