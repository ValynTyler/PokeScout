package com.example.trainer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeballWhite
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.PokemonImage
import com.example.trainer.presentation.state.Trainer

@Composable
fun DisplayScreen(
    nfc: PokemonNfcData,
    api: Trainer.ApiData.Success
) {
    Column(
        modifier = Modifier.padding(8.dp, vertical = 8.dp)
    ) {
        PokemonImage(id = api.species.id, modifier = Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer))
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
        val statList = listOf(
            Pair("trainer:", nfc.trainerName),
            Pair("pokemon:", api.species.name()),
            Pair("xp:", nfc.xp().toString()),
            Pair("stage:", api.evolution.stage(api.species.id).fold(
                onSuccess = { it.toString() },
                onFailure = { "" }
            ) + "/" + api.evolution.length()),
        )
        for (item in statList) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(color = Color.Gray, text = item.first)
                Text(color = PokeballWhite, text = item.second)
            }
        }
    }
}