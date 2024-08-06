package com.example.trainer.presentation.ui.gym.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.group.TrainerGroup
import com.example.pokemon.domain.model.group.TrainerGroup.filterGyms
import com.example.pokemon.domain.nfc.PokemonNfcData

@Composable
fun GymBadgeList(
    nfc: PokemonNfcData,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        for ((index, item) in TrainerGroup.Type.Intermediate.filterGyms().withIndex()) {
            GymBadgeListItem(gym = item, nfc.gymBadges[index])
        }
    }
}

@Preview
@Composable
fun GymBadgeListPreview() {
    val nfc = PokemonNfcData()
//    nfc.gymBadges[5] = true

    GymBadgeList(nfc = nfc)
}