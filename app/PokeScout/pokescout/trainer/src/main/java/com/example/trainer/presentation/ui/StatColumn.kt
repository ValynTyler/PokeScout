package com.example.trainer.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallLightGrey
import com.example.pokemon.presentation.theme.PokeBallWhite

@Composable
fun StatColumn(
    nfc: PokemonNfcData,
    api: Trainer.ApiData.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        val statList = listOf(
            Pair("trainer:", nfc.trainerName),
            Pair("pokemon:", api.species.name()),
            Pair("xp:", nfc.xp().toString()),
            Pair("stage:", api.evolution.stage(nfc.speciesId).fold(
                onSuccess = { it.toString() },
                onFailure = { "" }
            ) + "/" + api.evolution.length()),
        )
        for (item in statList) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(color = PokeBallLightGrey, text = item.first)
                Text(color = PokeBallWhite, text = item.second)
            }
        }
    }
}