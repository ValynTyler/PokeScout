package com.example.trainer.presentation.ui.gym

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.trainer.R
import com.example.trainer.presentation.ui.MenuButton
import com.example.trainer.presentation.ui.gym.grid.GymBadgeGrid
import com.example.trainer.presentation.ui.gym.list.GymBadgeList

@Composable
fun GymBadgeDisplay(
    modifier: Modifier = Modifier,
    nfc: PokemonNfcData,
    viewMode: Trainer.BadgeViewMode,
) {
    Box(modifier = modifier) {
        when (viewMode) {
            Trainer.BadgeViewMode.GridView -> GymBadgeGrid(nfc = nfc)
            Trainer.BadgeViewMode.ListView -> GymBadgeList(nfc = nfc)
        }
    }
}