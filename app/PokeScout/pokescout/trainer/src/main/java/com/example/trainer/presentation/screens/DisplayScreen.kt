package com.example.trainer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.pokeBallColors
import com.example.pokemon.presentation.ui.PokemonImage
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.StatColumn

@Composable
fun DisplayScreen(
    nfc: PokemonNfcData,
    api: Trainer.ApiData.Success
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        PokemonImage(id = api.species.id,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            progress = nfc.xpPercent(
                api.evolution.stage(api.species.id).getOrDefault(0),
                api.evolution.length()
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        StatColumn(
            nfc, api,
            Modifier
                .fillMaxSize()
                .background(pokeBallColors.emptyColor)
        )
    }
}