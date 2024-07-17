package com.example.trainer.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.PokeScoutTheme
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun MainView(
    state: TrainerState
) {
    PokeScoutTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PokemonCard(state)
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(TrainerState())
}