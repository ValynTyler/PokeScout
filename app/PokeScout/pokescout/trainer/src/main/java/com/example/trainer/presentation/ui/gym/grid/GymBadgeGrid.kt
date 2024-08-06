package com.example.trainer.presentation.ui.gym.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.model.group.TrainerGroup.filterGyms
import com.example.pokemon.domain.nfc.PokemonNfcData

@Composable
fun GymBadgeGrid(nfc: PokemonNfcData, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        val badgeSize = 80.dp
        val gyms = nfc.trainerGroup.filterGyms()
        var index = 0
        while (index < gyms.size) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    if (index < gyms.size) {
                        GymBadgeGridItem(gym = gyms[index], nfc.gymBadges[index], modifier = Modifier.size(badgeSize))
                    } else {
                        Spacer(modifier = Modifier.size(badgeSize))
                    }
                    index++
                }
            }
        }
    }
}

@Preview
@Composable
fun GymBadgeGridPreview() {
    GymBadgeGrid(nfc = PokemonNfcData())
}