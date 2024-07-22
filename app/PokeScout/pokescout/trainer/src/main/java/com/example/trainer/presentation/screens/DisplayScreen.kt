package com.example.trainer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import com.example.pokemon.presentation.PokemonImage
import com.example.trainer.presentation.state.Trainer

@Composable
fun DisplayScreen(
    data: Trainer.ApiData.Success
) {
    Column {
        PokemonImage(id = data.species.id)
        LinearProgressIndicator()
    }
}