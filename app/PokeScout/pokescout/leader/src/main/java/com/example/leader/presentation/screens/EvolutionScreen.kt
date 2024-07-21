package com.example.leader.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.presentation.PokemonImage

@Composable
@Preview
fun EvolutionScreen() {
    Column {
        PokemonImage(
            id = 25,
            modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}