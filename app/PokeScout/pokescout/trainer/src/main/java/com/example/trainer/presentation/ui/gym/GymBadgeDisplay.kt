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
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.MenuButton
import com.example.trainer.presentation.ui.gym.grid.GymBadgeGrid
import com.example.trainer.presentation.ui.gym.list.GymBadgeList

@Composable
fun GymBadgeDisplay(
    nfc: PokemonNfcData,
    viewMode: Trainer.BadgeViewMode,
    onPressed: (Trainer.Event.Menu) -> Unit,
) {
    // Content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp, horizontal = 24.dp)
            .padding(start = 24.dp)
    ) {
        when (viewMode) {
            Trainer.BadgeViewMode.GridView -> GymBadgeGrid(nfc = nfc)
            Trainer.BadgeViewMode.ListView -> GymBadgeList(nfc = nfc)
        }
    }

    // View Menu
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .padding(start = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            MenuButton(
                modifier = Modifier.size(32.dp),
                resourceId =  R.drawable.grid,
                isSelected = when (viewMode) {
                    Trainer.BadgeViewMode.GridView -> true
                    Trainer.BadgeViewMode.ListView -> false
                }
            ) { onPressed(Trainer.Event.Menu.View.GridPressed) }
            MenuButton(
                modifier = Modifier.size(32.dp),
                resourceId =  R.drawable.list,
                isSelected = when (viewMode) {
                    Trainer.BadgeViewMode.GridView -> false
                    Trainer.BadgeViewMode.ListView -> true
                }
            ) { onPressed(Trainer.Event.Menu.View.ListPressed) }
        }
    }
}